package com.hospitalizacion.service.controller;

import com.hospitalizacion.service.dto.ApiResponse;
import com.hospitalizacion.service.dto.CamaRequest;
import com.hospitalizacion.service.dto.CamaResponse;
import com.hospitalizacion.service.service.CamaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hospitalizacion/camas")
@RequiredArgsConstructor
public class CamaController {

    private final CamaService camaService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CamaResponse>>> listar(
            @RequestParam(required = false) String servicio,
            @RequestParam(required = false) String estado) {
        List<CamaResponse> data = camaService.listar(servicio, estado);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CamaResponse>> obtener(@PathVariable UUID id) {
        CamaResponse data = camaService.obtener(id);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<ApiResponse<List<CamaResponse>>> disponibles(
            @RequestParam(required = false) String servicio) {
        List<CamaResponse> data = camaService.listarDisponibles(servicio);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @PostMapping("/crear")
    public ResponseEntity<ApiResponse<CamaResponse>> crear(@Valid @RequestBody CamaRequest request) {
        CamaResponse data = camaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(data, "Cama creada exitosamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CamaResponse>> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody CamaRequest request) {
        CamaResponse data = camaService.actualizar(id, request);
        return ResponseEntity.ok(ApiResponse.ok(data, "Cama actualizada exitosamente"));
    }

    @PutMapping("/{id}/ocupar")
    public ResponseEntity<ApiResponse<CamaResponse>> ocupar(
            @PathVariable UUID id,
            @RequestBody CamaService.OcuparRequest request) {
        CamaResponse data = camaService.ocupar(id, request);
        return ResponseEntity.ok(ApiResponse.ok(data, "Cama ocupada exitosamente"));
    }

    @PutMapping("/{id}/liberar")
    public ResponseEntity<ApiResponse<CamaResponse>> liberar(@PathVariable UUID id) {
        CamaResponse data = camaService.liberar(id);
        return ResponseEntity.ok(ApiResponse.ok(data, "Cama liberada exitosamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable UUID id) {
        camaService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Cama eliminada exitosamente"));
    }
}
