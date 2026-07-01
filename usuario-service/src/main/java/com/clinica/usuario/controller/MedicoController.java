package com.clinica.usuario.controller;

import com.clinica.usuario.domain.Rol;
import com.clinica.usuario.dto.UsuarioResponse;
import com.clinica.usuario.mapper.UsuarioMapper;
import com.clinica.usuario.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/medicos")
public class MedicoController {

    private final UsuarioRepository repositorio;
    private final UsuarioMapper mapper;

    public MedicoController(UsuarioRepository repositorio, UsuarioMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarMedicos() {
        var medicos = repositorio.buscar(Rol.MEDICO, null).stream()
                .map(mapper::aResponse)
                .toList();
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerMedico(@PathVariable Long id) {
        var medico = repositorio.findById(id)
                .filter(u -> u.getRol() == Rol.MEDICO)
                .map(mapper::aResponse)
                .orElseThrow(() -> new RuntimeException("Medico no encontrado con id " + id));
        return ResponseEntity.ok(medico);
    }
}
