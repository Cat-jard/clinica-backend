package com.clinica.usuario.service;

import com.clinica.usuario.domain.EstadoUsuario;
import com.clinica.usuario.domain.Rol;
import com.clinica.usuario.domain.Usuario;
import com.clinica.usuario.dto.ActualizarUsuarioRequest;
import com.clinica.usuario.dto.CrearUsuarioRequest;
import com.clinica.usuario.dto.MedicoSyncEvent;
import com.clinica.usuario.dto.RolDto;
import com.clinica.usuario.dto.UsuarioResponse;
import com.clinica.usuario.exception.RecursoDuplicadoException;
import com.clinica.usuario.exception.RecursoNoEncontradoException;
import com.clinica.usuario.mapper.UsuarioMapper;
import com.clinica.usuario.messaging.UsuarioEventPublisher;
import com.clinica.usuario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/** Logica de negocio para la gestion de usuarios y roles. */
@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository repositorio;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioEventPublisher publisher;
    private final String passwordPorDefecto;

    public UsuarioService(UsuarioRepository repositorio,
                          UsuarioMapper mapper,
                          PasswordEncoder passwordEncoder,
                          UsuarioEventPublisher publisher,
                          @org.springframework.beans.factory.annotation.Value("${app.seed.default-password}") String passwordPorDefecto) {
        this.repositorio = repositorio;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.publisher = publisher;
        this.passwordPorDefecto = passwordPorDefecto;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar(String rol, String texto) {
        Rol rolFiltro = (rol == null || rol.isBlank() || "Todos".equalsIgnoreCase(rol)) ? null : Rol.desde(rol);
        String textoFiltro = (texto == null || texto.isBlank()) ? null : texto.trim();
        return repositorio.buscar(rolFiltro, textoFiltro).stream().map(mapper::aResponse).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtener(Long id) {
        return mapper.aResponse(buscarEntidad(id));
    }

    @Transactional(readOnly = true)
    public Map<String, Long> resumen() {
        long total = repositorio.count();
        long activos = repositorio.countByEstado(EstadoUsuario.ACTIVO);
        return Map.of("total", total, "activos", activos, "inactivos", total - activos);
    }

    public UsuarioResponse crear(CrearUsuarioRequest req) {
        if (repositorio.existsByDni(req.dni())) {
            throw new RecursoDuplicadoException("Ya existe un usuario con el DNI " + req.dni());
        }
        if (repositorio.existsByEmailIgnoreCase(req.email())) {
            throw new RecursoDuplicadoException("Ya existe un usuario con el correo " + req.email());
        }

        Rol rol = Rol.desde(req.rol());
        Usuario u = new Usuario();
        u.setDni(req.dni());
        u.setNombre(req.nombre().trim());
        u.setApellidos(req.apellidos().trim());
        u.setEmail(req.email().trim().toLowerCase());
        u.setTelefono(req.telefono());
        u.setRol(rol);
        u.setEspecialidad(rol == Rol.MEDICO ? req.especialidad() : null);
        u.setEstado(EstadoUsuario.desde(req.estado()));

        String clave = (req.password() == null || req.password().isBlank()) ? passwordPorDefecto : req.password();
        u.setPasswordHash(passwordEncoder.encode(clave));

        UsuarioResponse res = mapper.aResponse(repositorio.save(u));
        publisher.publishWelcomeEmail(res.dni(), res.email(), res.nombre(), res.apellidos());
        if (rol == Rol.MEDICO) {
            publisher.publishMedicoSync(new MedicoSyncEvent(u.getId(), res.dni(), res.nombre(), res.apellidos(), res.email(), res.telefono(), res.especialidad()));
        }
        return res;
    }

    public UsuarioResponse actualizar(Long id, ActualizarUsuarioRequest req) {
        Usuario u = buscarEntidad(id);

        repositorio.findByDni(req.dni())
                .filter(otro -> !otro.getId().equals(id))
                .ifPresent(otro -> { throw new RecursoDuplicadoException("El DNI " + req.dni() + " ya esta en uso"); });
        repositorio.findByEmailIgnoreCase(req.email())
                .filter(otro -> !otro.getId().equals(id))
                .ifPresent(otro -> { throw new RecursoDuplicadoException("El correo " + req.email() + " ya esta en uso"); });

        Rol rol = Rol.desde(req.rol());
        u.setDni(req.dni());
        u.setNombre(req.nombre().trim());
        u.setApellidos(req.apellidos().trim());
        u.setEmail(req.email().trim().toLowerCase());
        u.setTelefono(req.telefono());
        u.setRol(rol);
        u.setEspecialidad(rol == Rol.MEDICO ? req.especialidad() : null);
        if (req.estado() != null && !req.estado().isBlank()) {
            u.setEstado(EstadoUsuario.desde(req.estado()));
        }
        if (req.password() != null && !req.password().isBlank()) {
            u.setPasswordHash(passwordEncoder.encode(req.password()));
        }

        UsuarioResponse res = mapper.aResponse(repositorio.save(u));
        if (rol == Rol.MEDICO) {
            publisher.publishMedicoSync(new MedicoSyncEvent(u.getId(), res.dni(), res.nombre(), res.apellidos(), res.email(), res.telefono(), res.especialidad()));
        }
        return res;
    }

    /**
     * Activa/desactiva el usuario (toggle). Regla de oro: nunca se elimina,
     * solo se cambia su estado para conservar la trazabilidad.
     */
    public UsuarioResponse alternarEstado(Long id) {
        Usuario u = buscarEntidad(id);
        u.setEstado(u.estaActivo() ? EstadoUsuario.INACTIVO : EstadoUsuario.ACTIVO);
        return mapper.aResponse(repositorio.save(u));
    }

    @Transactional(readOnly = true)
    public List<RolDto> listarRoles() {
        return Arrays.stream(Rol.values()).map(RolDto::de).toList();
    }

    private Usuario buscarEntidad(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id " + id));
    }
}
