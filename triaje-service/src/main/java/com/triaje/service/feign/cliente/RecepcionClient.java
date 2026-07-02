package com.triaje.service.feign.cliente;

import com.triaje.service.feign.dto.ApiResponseWrapper;
import com.triaje.service.feign.dto.PacienteColaTriajeResponse;
import com.triaje.service.feign.dto.PacienteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "recepcion-service", url = "${recepcion.api.url:http://localhost:8081}")
public interface RecepcionClient {

    @GetMapping("/pacientes/{id}")
    ApiResponseWrapper<PacienteResponse> obtenerPaciente(@PathVariable("id") UUID id);

    @GetMapping("/api/cola/triaje")
    ApiResponseWrapper<List<PacienteColaTriajeResponse>> obtenerColaTriaje(@RequestParam("fecha") String fecha);

    @PutMapping("/api/cola/{id}/estado")
    ApiResponseWrapper<Void> actualizarEstadoCola(@PathVariable("id") UUID id, @RequestParam("estado") String estado);
}
