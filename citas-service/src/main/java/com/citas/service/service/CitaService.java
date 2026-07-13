package com.citas.service.service;

import com.citas.service.dto.*;
import com.citas.service.entity.CancelacionCita;
import com.citas.service.entity.Cita;
import com.citas.service.exception.BadRequestException;
import com.citas.service.exception.ConflictException;
import com.citas.service.exception.ResourceNotFoundException;
import com.citas.service.feign.RecepcionClient;
import com.citas.service.feign.UsuarioClient;
import com.citas.service.mapper.CitaMapper;
import com.citas.service.repository.CancelacionCitaRepository;
import com.citas.service.repository.CitaRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CitaService {

    private final CitaRepository citaRepository;
    private final CancelacionCitaRepository cancelacionRepository;
    private final CitaMapper citaMapper;
    private final RecepcionClient recepcionClient;
    private final UsuarioClient usuarioClient;

    public CitaResponse create(CitaRequest request) {
        validarPaciente(request.getPacienteId());
        validarMedico(request.getMedicoId());

        if (request.getHoraInicio().isAfter(request.getHoraFin()) ||
                request.getHoraInicio().equals(request.getHoraFin())) {
            throw new BadRequestException("La hora de inicio debe ser anterior a la hora de fin");
        }

        List<Cita> ocupadas = citaRepository.findOcupadasByMedicoAndFecha(
                request.getMedicoId(), request.getFechaCita());
        for (Cita c : ocupadas) {
            if (horariosSeSolapan(request.getHoraInicio(), request.getHoraFin(),
                    c.getHoraInicio(), c.getHoraFin())) {
                throw new ConflictException("El medico ya tiene una cita en ese horario");
            }
        }

        PacienteResponse paciente = obtenerPaciente(request.getPacienteId());

        Cita cita = citaMapper.toEntity(request);
        cita.setEstado("PROGRAMADA");
        cita.setNumeroHistoria(paciente.nroHistoria());
        cita = citaRepository.save(cita);
        return citaMapper.toResponse(cita);
    }

    @Transactional(readOnly = true)
    public CitaResponse findById(UUID id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));
        return citaMapper.toResponse(cita);
    }

    @Transactional(readOnly = true)
    public List<CitaResponse> findByPacienteId(UUID pacienteId) {
        return citaRepository.findByPacienteIdOrderByFechaCitaDesc(pacienteId)
                .stream()
                .map(citaMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<CitaResponse> findByMedicoId(Long medicoId, Pageable pageable) {
        return citaRepository.findByMedicoIdOrderByFechaCitaDesc(medicoId, pageable)
                .map(citaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<CitaResponse> findAll(String estado, LocalDate desde, LocalDate hasta, Pageable pageable) {
        if (estado != null || desde != null || hasta != null) {
            return citaRepository.findAll(pageable).map(citaMapper::toResponse);
        }
        return citaRepository.findAll(pageable).map(citaMapper::toResponse);
    }

    public CancelacionResponse cancelar(UUID citaId, CancelacionRequest request) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));

        if ("CANCELADA".equals(cita.getEstado())) {
            throw new ConflictException("La cita ya se encuentra cancelada");
        }

        cita.setEstado("CANCELADA");
        citaRepository.save(cita);

        CancelacionCita cancelacion = new CancelacionCita();
        cancelacion.setCita(cita);
        cancelacion.setMotivo(request.getMotivo());
        cancelacion.setCanceladoPor(request.getCanceladoPor());
        cancelacion.setFechaCancelacion(LocalDateTime.now());
        cancelacion = cancelacionRepository.save(cancelacion);

        return new CancelacionResponse(
                cancelacion.getId(),
                cancelacion.getCita().getId(),
                cancelacion.getMotivo(),
                cancelacion.getCanceladoPor(),
                cancelacion.getFechaCancelacion(),
                cancelacion.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public Map<String, Long> resumen() {
        return Map.of(
                "programadas", citaRepository.countByEstado("PROGRAMADA"),
                "atendidas", citaRepository.countByEstado("ATENDIDA"),
                "canceladas", citaRepository.countByEstado("CANCELADA")
        );
    }

    private void validarPaciente(UUID pacienteId) {
        obtenerPaciente(pacienteId);
    }

    private void validarMedico(Long medicoId) {
        try {
            usuarioClient.obtenerMedico(medicoId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Medico no encontrado con id " + medicoId);
        }
    }

    private PacienteResponse obtenerPaciente(UUID pacienteId) {
        try {
            var response = recepcionClient.obtenerPaciente(pacienteId);
            if (response.data() == null) {
                throw new ResourceNotFoundException("Paciente no encontrado con id " + pacienteId);
            }
            return response.data();
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Paciente no encontrado con id " + pacienteId);
        }
    }

    private boolean horariosSeSolapan(LocalTime inicioA, LocalTime finA, LocalTime inicioB, LocalTime finB) {
        return inicioA.isBefore(finB) && finA.isAfter(inicioB);
    }
}
