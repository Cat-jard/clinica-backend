package com.clinica.soporte.controller;

import com.clinica.soporte.dto.ActualizarTicketRequest;
import com.clinica.soporte.dto.CambiarEstadoRequest;
import com.clinica.soporte.dto.CrearTicketRequest;
import com.clinica.soporte.dto.TicketResponse;
import com.clinica.soporte.service.SoporteService;
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
 * Gestion de tickets de soporte. Cualquier usuario autenticado puede abrir un
 * ticket; la gestion (editar / cambiar estado) queda reservada a ADMIN y SOPORTE.
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final SoporteService soporteService;

    public TicketController(SoporteService soporteService) {
        this.soporteService = soporteService;
    }

    /** Lista tickets con filtros opcionales: ?estado=Abierto&prioridad=Alta&q=texto */
    @GetMapping
    public ResponseEntity<List<TicketResponse>> listar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String prioridad,
            @RequestParam(required = false, name = "q") String q) {
        return ResponseEntity.ok(soporteService.listar(estado, prioridad, q));
    }

    /** Conteo total y por estado para el tablero. */
    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Long>> resumen() {
        return ResponseEntity.ok(soporteService.resumen());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(soporteService.obtener(id));
    }

    /** Abre un ticket (cualquier usuario autenticado). */
    @PostMapping
    public ResponseEntity<TicketResponse> crear(@Valid @RequestBody CrearTicketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(soporteService.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SOPORTE')")
    public ResponseEntity<TicketResponse> actualizar(@PathVariable Long id,
                                                     @Valid @RequestBody ActualizarTicketRequest request) {
        return ResponseEntity.ok(soporteService.actualizar(id, request));
    }

    /** Cambia el estado del ticket (Abierto / En proceso / Resuelto / Cerrado). */
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('ADMIN', 'SOPORTE')")
    public ResponseEntity<TicketResponse> cambiarEstado(@PathVariable Long id,
                                                        @Valid @RequestBody CambiarEstadoRequest request) {
        return ResponseEntity.ok(soporteService.cambiarEstado(id, request));
    }
}
