package com.clinica.usuario.controller;

import com.clinica.usuario.dto.LoginRequest;
import com.clinica.usuario.dto.LoginResponse;
import com.clinica.usuario.dto.UsuarioResponse;
import com.clinica.usuario.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /** Inicia sesion y devuelve el token JWT + datos del usuario (incluye la ruta del modulo). */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /** Devuelve el usuario autenticado a partir del token. */
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> usuarioActual(Authentication authentication) {
        return ResponseEntity.ok(authService.usuarioActual(authentication.getName()));
    }
}
