package com.clinica.auditoria.service;

import com.clinica.auditoria.domain.AccionAuditoria;
import com.clinica.auditoria.domain.RegistroAuditoria;
import com.clinica.auditoria.dto.AuditoriaResponse;
import com.clinica.auditoria.dto.RegistrarAuditoriaRequest;
import com.clinica.auditoria.exception.RecursoNoEncontradoException;
import com.clinica.auditoria.mapper.AuditoriaMapper;
import com.clinica.auditoria.repository.RegistroAuditoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Logica de negocio de la bitacora de auditoria. */
@Service
@Transactional
public class AuditoriaService {

    private final RegistroAuditoriaRepository repositorio;
    private final AuditoriaMapper mapper;

    public AuditoriaService(RegistroAuditoriaRepository repositorio, AuditoriaMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    /** Registra un evento. Es la unica operacion de escritura (los registros son inmutables). */
    public AuditoriaResponse registrar(RegistrarAuditoriaRequest req) {
        RegistroAuditoria r = new RegistroAuditoria();
        r.setUsuarioEmail(limpiar(req.usuarioEmail()));
        r.setUsuarioNombre(limpiar(req.usuarioNombre()));
        r.setRol(limpiar(req.rol()));
        r.setAccion(AccionAuditoria.desde(req.accion()));
        r.setModulo(req.modulo().trim());
        r.setDescripcion(req.descripcion().trim());
        r.setEntidad(limpiar(req.entidad()));
        r.setIp(limpiar(req.ip()));
        return mapper.aResponse(repositorio.save(r));
    }

    @Transactional(readOnly = true)
    public List<AuditoriaResponse> listar(String accion, String modulo, String texto) {
        AccionAuditoria accionFiltro = (accion == null || accion.isBlank() || "Todas".equalsIgnoreCase(accion))
                ? null : AccionAuditoria.desde(accion);
        String moduloFiltro = (modulo == null || modulo.isBlank() || "Todos".equalsIgnoreCase(modulo)) ? null : modulo.trim();
        String textoFiltro = (texto == null || texto.isBlank()) ? null : texto.trim();
        return repositorio.buscar(accionFiltro, moduloFiltro, textoFiltro).stream().map(mapper::aResponse).toList();
    }

    @Transactional(readOnly = true)
    public AuditoriaResponse obtener(Long id) {
        return mapper.aResponse(repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Registro de auditoria no encontrado con id " + id)));
    }

    /** Conteo total y por tipo de accion para el dashboard de Soporte / TI. */
    @Transactional(readOnly = true)
    public Map<String, Long> resumen() {
        Map<String, Long> resumen = new LinkedHashMap<>();
        resumen.put("total", repositorio.count());
        for (AccionAuditoria a : AccionAuditoria.values()) {
            resumen.put(a.name().toLowerCase(), repositorio.countByAccion(a));
        }
        return resumen;
    }

    private String limpiar(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor.trim();
    }
}
