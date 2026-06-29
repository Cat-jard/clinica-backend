package com.clinica.usuario.service;

import com.clinica.usuario.domain.Usuario;
import com.clinica.usuario.dto.LoginRequest;
import com.clinica.usuario.dto.LoginResponse;
import com.clinica.usuario.mapper.UsuarioMapper;
import com.clinica.usuario.repository.UsuarioRepository;
import com.clinica.usuario.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/** Autenticacion (login) y emision del token JWT. */
@Service
@Transactional
public class AuthService {

    private final UsuarioRepository repositorio;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UsuarioMapper mapper;

    public AuthService(UsuarioRepository repositorio,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       UsuarioMapper mapper) {
        this.repositorio = repositorio;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.mapper = mapper;
    }

    public LoginResponse login(LoginRequest req) {
        Usuario usuario = repositorio.findByEmailIgnoreCase(req.email())
                .orElseThrow(() -> new BadCredentialsException("Credenciales invalidas"));

        if (!passwordEncoder.matches(req.password(), usuario.getPasswordHash())) {
            throw new BadCredentialsException("Credenciales invalidas");
        }
        if (!usuario.estaActivo()) {
            throw new DisabledException("Cuenta inactiva");
        }

        usuario.setUltimoAcceso(LocalDateTime.now());
        repositorio.save(usuario);

        String token = jwtService.generarToken(usuario);
        return LoginResponse.de(token, jwtService.getExpiracionMs(), mapper.aResponse(usuario));
    }

    @Transactional(readOnly = true)
    public com.clinica.usuario.dto.UsuarioResponse usuarioActual(String email) {
        return repositorio.findByEmailIgnoreCase(email)
                .map(mapper::aResponse)
                .orElseThrow(() -> new BadCredentialsException("Sesion no valida"));
    }
}
