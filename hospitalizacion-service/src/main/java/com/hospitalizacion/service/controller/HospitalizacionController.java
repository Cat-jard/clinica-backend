package com.hospitalizacion.service.controller;

import com.hospitalizacion.service.dto.*;
import com.hospitalizacion.service.dto.CamaResponse;
import com.hospitalizacion.service.service.HospitalizacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/hospitalizacion")
@RequiredArgsConstructor
public class HospitalizacionController {

    private final HospitalizacionService service;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<HospitalizacionResponse>>> listar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String servicio,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<HospitalizacionResponse> data = service.listar(estado, servicio, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HospitalizacionResponse>> obtener(@PathVariable UUID id) {
        HospitalizacionResponse data = service.obtenerPorId(id);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<ApiResponse<HospitalizacionResponse>> obtenerActiva(@PathVariable UUID pacienteId) {
        HospitalizacionResponse data = service.obtenerActivaPorPaciente(pacienteId);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/camas-disponibles")
    public ResponseEntity<ApiResponse<List<CamaResponse>>> camasDisponibles(
            @RequestParam String servicio) {
        List<CamaResponse> data = service.obtenerCamasDisponibles(servicio);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @PostMapping("/ingreso")
    public ResponseEntity<ApiResponse<HospitalizacionResponse>> ingreso(
            @Valid @RequestBody IngresoRequest request) {
        HospitalizacionResponse data = service.registrarIngreso(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(data, "Ingreso hospitalario registrado"));
    }

    @PostMapping("/{id}/autorizacion")
    public ResponseEntity<ApiResponse<AutorizacionResponse>> autorizacion(
            @PathVariable UUID id,
            @Valid @RequestBody AutorizacionRequest request) {
        AutorizacionResponse data = service.generarAutorizacion(id, request);
        return ResponseEntity.ok(ApiResponse.ok(data, "Autorizacion registrada"));
    }

    @PutMapping("/{id}/trasladar")
    public ResponseEntity<ApiResponse<HospitalizacionResponse>> trasladar(
            @PathVariable UUID id,
            @Valid @RequestBody TrasladoRequest request) {
        HospitalizacionResponse data = service.trasladar(id, request);
        return ResponseEntity.ok(ApiResponse.ok(data, "Traslado realizado"));
    }

    @PostMapping("/{id}/alta")
    public ResponseEntity<ApiResponse<HospitalizacionResponse>> alta(
            @PathVariable UUID id,
            @Valid @RequestBody AltaRequest request) {
        HospitalizacionResponse data = service.darAlta(id, request);
        return ResponseEntity.ok(ApiResponse.ok(data, "Alta hospitalaria registrada"));
    }

    @GetMapping("/{id}/epicrisis")
    public ResponseEntity<ApiResponse<EpicrisisResponse>> epicrisis(@PathVariable UUID id) {
        EpicrisisResponse data = service.obtenerEpicrisis(id);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/dashboard/ocupacion")
    public ResponseEntity<ApiResponse<Map<String, Object>>> ocupacion() {
        Map<String, Object> data = service.obtenerOcupacion();
        return ResponseEntity.ok(ApiResponse.ok(data));
    }
}
