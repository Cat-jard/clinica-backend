package com.citas.service.feign;

import com.citas.service.dto.MedicoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "usuario-service")
public interface UsuarioClient {

    @GetMapping("/api/public/medicos")
    List<MedicoResponse> listarMedicos();

    @GetMapping("/api/public/medicos/{id}")
    MedicoResponse obtenerMedico(@PathVariable("id") Long id);
}
