package com.recepcion.service.service;

import com.recepcion.service.dto.ConsentimientoResponse;
import com.recepcion.service.dto.CreateConsentimientoRequest;
import com.recepcion.service.entity.ConsentimientoInformado;
import com.recepcion.service.entity.Paciente;
import com.recepcion.service.exception.ResourceNotFoundException;
import com.recepcion.service.mapper.ConsentimientoMapper;
import com.recepcion.service.repository.ConsentimientoInformadoRepository;
import com.recepcion.service.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsentimientoService {

    private final ConsentimientoInformadoRepository consentimientoRepository;
    private final PacienteRepository pacienteRepository;
    private final ConsentimientoMapper consentimientoMapper;

    public ConsentimientoResponse create(UUID pacienteId, CreateConsentimientoRequest request) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        ConsentimientoInformado entity = consentimientoMapper.toEntity(request);
        entity.setPaciente(paciente);
        entity.setTextoLegalHash(sha256(request.getTextoLegal()));

        if (request.getFirmaBase64() != null && !request.getFirmaBase64().isBlank()) {
            entity.setFirmaHash(sha256(request.getFirmaBase64()));
        }

        entity.setFechaExposicion(LocalDateTime.now());

        if (Boolean.TRUE.equals(request.getAceptado()) && request.getFirmaBase64() != null && !request.getFirmaBase64().isBlank()) {
            entity.setFechaFirma(LocalDateTime.now());
        }

        entity = consentimientoRepository.save(entity);
        return consentimientoMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<ConsentimientoResponse> findAllByPacienteId(UUID pacienteId) {
        if (!pacienteRepository.existsById(pacienteId)) {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
        return consentimientoRepository.findByPacienteIdOrderByCreatedAtDesc(pacienteId)
                .stream()
                .map(consentimientoMapper::toResponse)
                .toList();
    }

    private String sha256(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 no disponible", e);
        }
    }
}
