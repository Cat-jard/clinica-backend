package com.recepcion.service.controller;

import com.recepcion.service.dto.*;
import com.recepcion.service.service.ConsentimientoService;
import com.recepcion.service.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;
    private final ConsentimientoService consentimientoService;

    @PostMapping("/crear")
    public ResponseEntity<ApiResponse<PacienteResponse>> create(@Valid @RequestBody CreatePacienteRequest request) {
        PacienteResponse data = pacienteService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(data, "Paciente creado exitosamente"));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<PacienteResumenResponse>>> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<PacienteResumenResponse> data = pacienteService.findAll(q, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PacienteResponse>> findById(@PathVariable UUID id) {
        PacienteResponse data = pacienteService.findById(id);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PacienteResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody CreatePacienteRequest request) {
        PacienteResponse data = pacienteService.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok(data, "Paciente actualizado exitosamente"));
    }

    @PostMapping("/{id}/consentimiento")
    public ResponseEntity<ApiResponse<ConsentimientoResponse>> createConsentimiento(
            @PathVariable UUID id,
            @Valid @RequestBody CreateConsentimientoRequest request) {
        ConsentimientoResponse data = consentimientoService.create(id, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(data, "Consentimiento registrado exitosamente"));
    }

    @GetMapping("/{id}/consentimientos")
    public ResponseEntity<ApiResponse<List<ConsentimientoResponse>>> listConsentimientos(@PathVariable UUID id) {
        List<ConsentimientoResponse> data = consentimientoService.findAllByPacienteId(id);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }
}
