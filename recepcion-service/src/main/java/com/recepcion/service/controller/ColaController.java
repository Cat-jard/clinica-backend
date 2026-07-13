package com.recepcion.service.controller;

import com.recepcion.service.dto.ApiResponse;
import com.recepcion.service.dto.ColaTriajeResponse;
import com.recepcion.service.dto.IngresarColaRequest;
import com.recepcion.service.service.ColaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cola")
@RequiredArgsConstructor
public class ColaController {

    private final ColaService colaService;

    @PostMapping("/ingresar")
    public ResponseEntity<ApiResponse<ColaTriajeResponse>> ingresar(@Valid @RequestBody IngresarColaRequest request) {
        ColaTriajeResponse data = colaService.ingresar(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(data, "Paciente ingresado a cola exitosamente"));
    }

    @GetMapping("/triaje")
    public ResponseEntity<ApiResponse<List<ColaTriajeResponse>>> obtenerColaTriaje(
            @RequestParam(required = false) LocalDate fecha) {
        LocalDate fechaConsulta = fecha != null ? fecha : LocalDate.now();
        List<ColaTriajeResponse> data = colaService.obtenerColaTriaje(fechaConsulta);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<Void>> actualizarEstado(
            @PathVariable UUID id,
            @RequestParam String estado) {
        colaService.actualizarEstado(id, estado);
        return ResponseEntity.ok(ApiResponse.ok(null, "Estado actualizado exitosamente"));
    }
}
