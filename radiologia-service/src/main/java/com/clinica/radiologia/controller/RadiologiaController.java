package com.clinica.radiologia.controller;

import com.clinica.radiologia.dto.EstudioImagenDto;
import com.clinica.radiologia.dto.FirmarInformeRequest;
import com.clinica.radiologia.dto.InformeRadiologicoDto;
import com.clinica.radiologia.dto.RadiologiaSolicitudRequest;
import com.clinica.radiologia.service.RadiologiaService;
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
@RequestMapping("/api/radiologia")
public class RadiologiaController {
    private final RadiologiaService radiologiaService;

    public RadiologiaController(RadiologiaService radiologiaService) {
        this.radiologiaService = radiologiaService;
    }

    @PostMapping("/estudios/desde-solicitud")
    @ResponseStatus(HttpStatus.CREATED)
    public EstudioImagenDto crearDesdeSolicitud(@Valid @RequestBody RadiologiaSolicitudRequest request) {
        return radiologiaService.crearDesdeSolicitud(request);
    }

    @GetMapping("/estudios")
    public List<EstudioImagenDto> listar() {
        return radiologiaService.listar();
    }

    @GetMapping("/estudios/{id}")
    public EstudioImagenDto obtener(@PathVariable String id) {
        return radiologiaService.obtener(id);
    }

    @PatchMapping("/estudios/{id}/iniciar")
    public EstudioImagenDto iniciar(@PathVariable String id) {
        return radiologiaService.iniciarLectura(id);
    }

    @PatchMapping("/estudios/{id}/borrador")
    public EstudioImagenDto guardarBorrador(@PathVariable String id, @RequestBody InformeRadiologicoDto informe) {
        return radiologiaService.guardarBorrador(id, informe);
    }

    @PatchMapping("/estudios/{id}/firmar")
    public EstudioImagenDto firmar(@PathVariable String id, @RequestBody FirmarInformeRequest request) {
        return radiologiaService.firmar(id, request);
    }
}
