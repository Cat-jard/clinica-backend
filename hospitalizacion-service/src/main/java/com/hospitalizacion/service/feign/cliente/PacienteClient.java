package com.hospitalizacion.service.feign.cliente;

import com.hospitalizacion.service.feign.dto.ApiResponseWrapper;
import com.hospitalizacion.service.feign.dto.PacienteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "recepcion-service", url = "${recepcion.api.url:http://localhost:8081}")
public interface PacienteClient {

    @GetMapping("/pacientes/{id}")
    ApiResponseWrapper<PacienteResponse> obtenerPaciente(@PathVariable("id") UUID id);
}
