package com.clinica.usuario.controller;

import com.clinica.usuario.dto.ActualizarUsuarioRequest;
import com.clinica.usuario.dto.CrearUsuarioRequest;
import com.clinica.usuario.dto.RolDto;
import com.clinica.usuario.dto.UsuarioResponse;
import com.clinica.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Gestion de usuarios y roles. Las operaciones de escritura estan restringidas
 * a los roles ADMIN y SOPORTE (RBAC).
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /** Lista usuarios con filtros opcionales: ?rol=Médico&q=texto */
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar(
            @RequestParam(required = false) String rol,
            @RequestParam(required = false, name = "q") String q) {
        return ResponseEntity.ok(usuarioService.listar(rol, q));
    }

    /** Conteo de usuarios (total / activos / inactivos) para el dashboard. */
    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Long>> resumen() {
        return ResponseEntity.ok(usuarioService.resumen());
    }

    /** Catalogo de roles disponibles. */
    @GetMapping("/roles")
    public ResponseEntity<List<RolDto>> roles() {
        return ResponseEntity.ok(usuarioService.listarRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtener(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SOPORTE')")
    public ResponseEntity<UsuarioResponse> crear(@Valid @RequestBody CrearUsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SOPORTE')")
    public ResponseEntity<UsuarioResponse> actualizar(@PathVariable Long id,
                                                       @Valid @RequestBody ActualizarUsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    /** Activa/desactiva el usuario (no se elimina, solo se desactiva). */
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('ADMIN', 'SOPORTE')")
    public ResponseEntity<UsuarioResponse> alternarEstado(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.alternarEstado(id));
    }
}
