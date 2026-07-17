package com.historiaclinica.service.controller;

import com.historiaclinica.service.dto.*;
import com.historiaclinica.service.service.AtencionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/atenciones")
@RequiredArgsConstructor
public class AtencionController {

    private final AtencionService atencionService;

    @PostMapping
    public ResponseEntity<ApiResponse<AtencionResponse>> iniciarAtencion(
            @Valid @RequestBody IniciarAtencionRequest request) {
        AtencionResponse data = atencionService.iniciarAtencion(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(data, "Atencion iniciada"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AtencionResponse>> obtenerAtencion(@PathVariable UUID id) {
        AtencionResponse data = atencionService.obtenerAtencion(id);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/activa")
    public ResponseEntity<ApiResponse<AtencionResponse>> buscarActiva(
            @RequestParam UUID pacienteId,
            @RequestParam Long medicoId) {
        AtencionResponse data = atencionService.buscarOBorradorActivo(pacienteId, medicoId);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AtencionResponse>> guardarAtencion(
            @PathVariable UUID id,
            @Valid @RequestBody GuardarAtencionRequest request) {
        AtencionResponse data = atencionService.guardarAtencion(id, request);
        return ResponseEntity.ok(ApiResponse.ok(data, "Atencion guardada"));
    }

    @PostMapping("/{id}/firmar")
    public ResponseEntity<ApiResponse<AtencionResponse>> firmarAtencion(
            @PathVariable UUID id,
            @Valid @RequestBody FirmarAtencionRequest request) {
        AtencionResponse data = atencionService.firmarAtencion(id, request);
        return ResponseEntity.ok(ApiResponse.ok(data, "Atencion firmada exitosamente"));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<ApiResponse<List<AtencionResponse>>> listarPorPaciente(
            @PathVariable UUID pacienteId) {
        List<AtencionResponse> data = atencionService.listarPorPaciente(pacienteId);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/paciente/{pacienteId}/historial")
    public ResponseEntity<ApiResponse<List<HistorialPacienteItem>>> listarHistorial(
            @PathVariable UUID pacienteId) {
        List<HistorialPacienteItem> data = atencionService.listarHistorialPaciente(pacienteId);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }
}
