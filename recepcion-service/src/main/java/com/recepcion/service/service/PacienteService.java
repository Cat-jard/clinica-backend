package com.recepcion.service.service;

import com.recepcion.service.dto.CreatePacienteRequest;
import com.recepcion.service.dto.PacienteResumenResponse;
import com.recepcion.service.dto.PacienteResponse;
import com.recepcion.service.entity.Paciente;
import com.recepcion.service.exception.DuplicatedDocumentException;
import com.recepcion.service.exception.ResourceNotFoundException;
import com.recepcion.service.mapper.PacienteMapper;
import com.recepcion.service.repository.ConsentimientoInformadoRepository;
import com.recepcion.service.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ConsentimientoInformadoRepository consentimientoRepository;
    private final PacienteMapper pacienteMapper;

    public PacienteResponse create(CreatePacienteRequest request) {
        if (pacienteRepository.existsByNroDocumento(request.getNroDocumento())) {
            throw new DuplicatedDocumentException("El número de documento ya está registrado");
        }
        Paciente paciente = pacienteMapper.toEntity(request);
        paciente.setNroHistoria(generarNroHistoria());
        paciente = pacienteRepository.save(paciente);
        paciente.setConsentimiento("Pendiente");
        return pacienteMapper.toResponse(paciente);
    }

    @Transactional(readOnly = true)
    public PacienteResponse findById(UUID id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
        setConsentimientoVirtual(paciente);
        return pacienteMapper.toResponse(paciente);
    }

    @Transactional(readOnly = true)
    public Page<PacienteResumenResponse> findAll(String q, Pageable pageable) {
        Page<Paciente> page = (q != null && !q.isBlank())
                ? pacienteRepository.search(q, pageable)
                : pacienteRepository.findAll(pageable);
        page.getContent().forEach(this::setConsentimientoVirtual);
        return page.map(pacienteMapper::toResumenResponse);
    }

    public PacienteResponse update(UUID id, CreatePacienteRequest request) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        if (!paciente.getNroDocumento().equals(request.getNroDocumento())
                && pacienteRepository.existsByNroDocumento(request.getNroDocumento())) {
            throw new DuplicatedDocumentException("El número de documento ya está registrado por otro paciente");
        }

        pacienteMapper.updateEntity(request, paciente);
        paciente = pacienteRepository.save(paciente);
        setConsentimientoVirtual(paciente);
        return pacienteMapper.toResponse(paciente);
    }

    private String generarNroHistoria() {
        String year = String.valueOf(Year.now().getValue());
        String prefix = "HC-" + year;
        var opt = pacienteRepository.findLastNroHistoriaByPrefix(prefix + "%");
        if (opt.isPresent()) {
            String last = opt.get();
            String numStr = last.substring(prefix.length());
            int correlativo = Integer.parseInt(numStr) + 1;
            return prefix + String.format("%07d", correlativo);
        }
        return prefix + "0000001";
    }

    private void setConsentimientoVirtual(Paciente paciente) {
        boolean firmado = consentimientoRepository.existsByPacienteIdAndAceptadoTrue(paciente.getId());
        paciente.setConsentimiento(firmado ? "Firmado" : "Pendiente");
    }
}
