package com.triaje.service.controller;

import com.triaje.service.dto.*;
import com.triaje.service.service.TriajeService;
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
@RequestMapping("/api/triaje")
@RequiredArgsConstructor
public class TriajeController {

    private final TriajeService triajeService;

    @GetMapping("/cola")
    public ResponseEntity<ApiResponse<List<ColaTriajeResponse>>> obtenerCola(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<ColaTriajeResponse> data = triajeService.obtenerColaTriaje(fecha);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @PostMapping("/cola/{colaId}/iniciar")
    public ResponseEntity<ApiResponse<Void>> iniciarTriaje(@PathVariable UUID colaId) {
        triajeService.iniciarTriaje(colaId);
        return ResponseEntity.ok(ApiResponse.ok(null, "Triaje iniciado"));
    }

    @GetMapping("/pacientes/{pacienteId}")
    public ResponseEntity<ApiResponse<?>> obtenerDatosPaciente(@PathVariable UUID pacienteId) {
        var data = triajeService.obtenerDatosPaciente(pacienteId);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @PostMapping("/registrar")
    public ResponseEntity<ApiResponse<RegistroTriajeResponse>> registrarTriaje(
            @Valid @RequestBody RegistrarTriajeRequest request) {
        RegistroTriajeResponse data = triajeService.registrarTriaje(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(data, "Triaje registrado exitosamente"));
    }

    @GetMapping("/registros/{pacienteId}/ultimo")
    public ResponseEntity<ApiResponse<RegistroTriajeResponse>> obtenerUltimoRegistro(
            @PathVariable UUID pacienteId) {
        RegistroTriajeResponse data = triajeService.obtenerUltimoRegistro(pacienteId);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/registros")
    public ResponseEntity<ApiResponse<Page<RegistroTriajeResponse>>> listarRegistros(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) String prioridad,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<RegistroTriajeResponse> data = triajeService.listarRegistros(fecha, prioridad, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @PostMapping("/pacientes/{pacienteId}/kardex")
    public ResponseEntity<ApiResponse<EntradaKardexResponse>> crearKardex(
            @PathVariable UUID pacienteId,
            @Valid @RequestBody CrearKardexRequest request) {
        request.setPacienteId(pacienteId);
        EntradaKardexResponse data = triajeService.crearKardex(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(data, "Entrada de kardex creada"));
    }

    @PutMapping("/kardex/{id}/firmar")
    public ResponseEntity<ApiResponse<EntradaKardexResponse>> firmarKardex(
            @PathVariable UUID id,
            @Valid @RequestBody FirmarKardexRequest request) {
        EntradaKardexResponse data = triajeService.firmarKardex(id, request);
        return ResponseEntity.ok(ApiResponse.ok(data, "Kardex firmado exitosamente"));
    }

    @GetMapping("/pacientes/{pacienteId}/kardex")
    public ResponseEntity<ApiResponse<List<EntradaKardexResponse>>> listarKardex(
            @PathVariable UUID pacienteId) {
        List<EntradaKardexResponse> data = triajeService.listarKardex(pacienteId);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/observacion")
    public ResponseEntity<ApiResponse<List<ObservacionPacienteResponse>>> listarObservaciones() {
        List<ObservacionPacienteResponse> data = triajeService.listarObservaciones();
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @PutMapping("/observacion/{id}/alta")
    public ResponseEntity<ApiResponse<ObservacionPacienteResponse>> darAlta(
            @PathVariable UUID id,
            @Valid @RequestBody AltaRequest request) {
        ObservacionPacienteResponse data = triajeService.darAlta(id, request);
        return ResponseEntity.ok(ApiResponse.ok(data, "Alta registrada: " + request.getTipoAlta()));
    }

    @PostMapping("/kardex/{id}/reevaluacion")
    public ResponseEntity<ApiResponse<EntradaKardexResponse>> solicitarReevaluacion(@PathVariable UUID id) {
        EntradaKardexResponse data = triajeService.solicitarReevaluacion(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(data, "Reevaluacion solicitada"));
    }

    @GetMapping("/dashboard/kpi")
    public ResponseEntity<ApiResponse<Map<String, Object>>> kpi(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        Map<String, Object> data = triajeService.obtenerKPIs(fecha);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/dashboard/distribucion-prioridades")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> distribucionPrioridades(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Map<String, Object>> data = triajeService.distribucionPrioridades(fecha);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/dashboard/llegadas-por-hora")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> llegadasPorHora(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Map<String, Object>> data = triajeService.llegadasPorHora(fecha);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/dashboard/top-motivos")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> topMotivos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Map<String, Object>> data = triajeService.topMotivos(fecha);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/dashboard/spo2-promedio")
    public ResponseEntity<ApiResponse<Map<String, Object>>> spo2Promedio(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        Map<String, Object> data = triajeService.spo2Promedio(fecha);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }
}
