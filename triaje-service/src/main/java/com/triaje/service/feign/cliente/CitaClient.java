package com.triaje.service.feign.cliente;

import com.triaje.service.feign.dto.ApiResponseWrapper;
import com.triaje.service.feign.dto.CitaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "citas-service", url = "${citas.api.url:http://localhost:8082}")
public interface CitaClient {

    @GetMapping("/api/citas/paciente/{pacienteId}")
    ApiResponseWrapper<List<CitaResponse>> listarCitasPorPaciente(@PathVariable("pacienteId") UUID pacienteId);

    @GetMapping("/api/citas/{id}")
    ApiResponseWrapper<CitaResponse> obtenerCita(@PathVariable("id") UUID id);
}
