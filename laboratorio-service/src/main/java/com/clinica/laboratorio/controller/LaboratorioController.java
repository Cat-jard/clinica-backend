package com.clinica.laboratorio.controller;

import com.clinica.laboratorio.dto.IngresarResultadosRequest;
import com.clinica.laboratorio.dto.OrdenLabDto;
import com.clinica.laboratorio.dto.RegistrarMuestraRequest;
import com.clinica.laboratorio.dto.SolicitudExamenesRequest;
import com.clinica.laboratorio.dto.SolicitudExamenesResponse;
import com.clinica.laboratorio.service.LaboratorioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class LaboratorioController {
    private final LaboratorioService laboratorioService;

    public LaboratorioController(LaboratorioService laboratorioService) {
        this.laboratorioService = laboratorioService;
    }

    @PostMapping("/api/solicitudes-examenes")
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudExamenesResponse crearSolicitud(@Valid @RequestBody SolicitudExamenesRequest request) {
        return laboratorioService.crearSolicitud(request);
    }

    @GetMapping("/api/laboratorio/ordenes")
    public List<OrdenLabDto> listarOrdenes() {
        return laboratorioService.listarOrdenes();
    }

    @GetMapping("/api/laboratorio/ordenes/{id}")
    public OrdenLabDto obtenerOrden(@PathVariable String id) {
        return laboratorioService.obtener(id);
    }

    @PatchMapping("/api/laboratorio/ordenes/{id}/muestra")
    public OrdenLabDto registrarMuestra(@PathVariable String id, @RequestBody RegistrarMuestraRequest request) {
        return laboratorioService.registrarMuestra(id, request);
    }

    @PatchMapping("/api/laboratorio/ordenes/{id}/resultados")
    public OrdenLabDto ingresarResultados(@PathVariable String id, @RequestBody IngresarResultadosRequest request) {
        return laboratorioService.ingresarResultados(id, request);
    }

    @PatchMapping("/api/laboratorio/ordenes/{id}/validar")
    public OrdenLabDto validar(@PathVariable String id) {
        return laboratorioService.validar(id);
    }
}
