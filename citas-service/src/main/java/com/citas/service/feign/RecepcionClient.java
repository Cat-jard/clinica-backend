package com.citas.service.feign;

import com.citas.service.dto.ApiResponse;
import com.citas.service.dto.PacienteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "recepcion-service")
public interface RecepcionClient {

    @GetMapping("/pacientes/{id}")
    ApiResponse<PacienteResponse> obtenerPaciente(@PathVariable("id") UUID id);
}
