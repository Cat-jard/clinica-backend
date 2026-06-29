package com.clinica.auditoria.controller;

import com.clinica.auditoria.dto.AuditoriaResponse;
import com.clinica.auditoria.dto.RegistrarAuditoriaRequest;
import com.clinica.auditoria.service.AuditoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Bitacora de auditoria. El registro de eventos lo realizan los demas
 * microservicios; la consulta esta reservada a los roles ADMIN y SOPORTE.
 */
@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    /** Registra un evento (lo llaman los demas servicios, p. ej. tras un login). */
    @PostMapping
    public ResponseEntity<AuditoriaResponse> registrar(@Valid @RequestBody RegistrarAuditoriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(auditoriaService.registrar(request));
    }

    /** Lista la bitacora con filtros opcionales: ?accion=Creación&modulo=Usuarios&q=texto */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SOPORTE')")
    public ResponseEntity<List<AuditoriaResponse>> listar(
            @RequestParam(required = false) String accion,
            @RequestParam(required = false) String modulo,
            @RequestParam(required = false, name = "q") String q) {
        return ResponseEntity.ok(auditoriaService.listar(accion, modulo, q));
    }

    /** Conteo total y por tipo de accion para el dashboard. */
    @GetMapping("/resumen")
    @PreAuthorize("hasAnyRole('ADMIN', 'SOPORTE')")
    public ResponseEntity<Map<String, Long>> resumen() {
        return ResponseEntity.ok(auditoriaService.resumen());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SOPORTE')")
    public ResponseEntity<AuditoriaResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(auditoriaService.obtener(id));
    }
}
