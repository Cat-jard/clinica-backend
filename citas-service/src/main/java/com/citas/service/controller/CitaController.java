package com.citas.service.controller;

import com.citas.service.dto.*;
import com.citas.service.service.CitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @PostMapping("/crear")
    public ResponseEntity<ApiResponse<CitaResponse>> create(@Valid @RequestBody CitaRequest request) {
        CitaResponse data = citaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(data, "Cita creada exitosamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaResponse>> findById(@PathVariable UUID id) {
        CitaResponse data = citaService.findById(id);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<CitaResponse>>> findAll(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<CitaResponse> data = citaService.findAll(estado, desde, hasta, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<ApiResponse<List<CitaResponse>>> findByPaciente(@PathVariable UUID pacienteId) {
        List<CitaResponse> data = citaService.findByPacienteId(pacienteId);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<ApiResponse<Page<CitaResponse>>> findByMedico(
            @PathVariable Long medicoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<CitaResponse> data = citaService.findByMedicoId(medicoId, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<ApiResponse<CancelacionResponse>> cancelar(
            @PathVariable UUID id,
            @Valid @RequestBody CancelacionRequest request) {
        CancelacionResponse data = citaService.cancelar(id, request);
        return ResponseEntity.ok(ApiResponse.ok(data, "Cita cancelada exitosamente"));
    }

    @GetMapping("/resumen")
    public ResponseEntity<ApiResponse<Map<String, Long>>> resumen() {
        Map<String, Long> data = citaService.resumen();
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @PostMapping("/paciente/{pacienteId}/atender")
    public ResponseEntity<ApiResponse<CitaResponse>> atenderCitaHoy(@PathVariable UUID pacienteId) {
        CitaResponse data = citaService.atenderCitaHoy(pacienteId);
        return ResponseEntity.ok(ApiResponse.ok(data, "Cita marcada como atendida"));
    }
}
