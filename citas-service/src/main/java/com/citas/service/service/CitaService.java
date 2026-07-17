package com.citas.service.service;

import com.citas.service.dto.*;
import com.citas.service.entity.CancelacionCita;
import com.citas.service.entity.Cita;
import com.citas.service.exception.BadRequestException;
import com.citas.service.exception.ConflictException;
import com.citas.service.exception.ResourceNotFoundException;
import com.citas.service.mapper.CitaMapper;
import com.citas.service.messaging.CitasEventPublisher;
import com.citas.service.repository.CancelacionCitaRepository;
import com.citas.service.repository.CitaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final CitasEventPublisher eventPublisher;

    public CitaResponse create(CitaRequest request) {
        var medico = obtenerMedico(request.getMedicoId());
        String medicoNombre = medico.nombre() + " " + medico.apellidos();

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
        String pacienteNombre = paciente.nombres() + " " + paciente.apellidoPaterno() + " " + (paciente.apellidoMaterno() != null ? paciente.apellidoMaterno() : "");

        Cita cita = citaMapper.toEntity(request);
        cita.setEstado("PROGRAMADA");
        cita.setNumeroHistoria(paciente.nroHistoria());
        cita.setPacienteNombre(pacienteNombre.trim());
        cita.setMedicoNombre(medicoNombre);
        cita = citaRepository.save(cita);

        // Publicar evento de cita creada
        eventPublisher.publishAppointmentCreated(
                paciente.email(),
                cita.getPacienteNombre(),
                cita.getMedicoId().toString(),
                cita.getMedicoNombre(),
                medico.especialidad(),
                cita.getFechaCita().toString(),
                cita.getHoraInicio().toString()
        );

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
        if (desde != null && hasta != null) {
            List<Cita> citas = citaRepository.findPorRangoFechas(desde, hasta);
            if (estado != null) {
                citas = citas.stream().filter(c -> c.getEstado().equals(estado)).toList();
            }
            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), citas.size());
            if (start >= citas.size()) {
                return Page.empty();
            }
            List<CitaResponse> content = citas.subList(start, end).stream()
                    .map(citaMapper::toResponse).toList();
            return new PageImpl<>(content, pageable, citas.size());
        }
        return citaRepository.findAll(pageable).map(citaMapper::toResponse);
    }

    public CancelacionResponse cancelar(UUID citaId, CancelacionRequest request) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));

        if ("CANCELADA".equals(cita.getEstado())) {
            throw new ConflictException("La cita ya se encuentra cancelada");
        }

        PacienteResponse paciente = obtenerPaciente(cita.getPacienteId());
        var medico = obtenerMedico(cita.getMedicoId());

        cita.setEstado("CANCELADA");
        citaRepository.save(cita);

        CancelacionCita cancelacion = new CancelacionCita();
        cancelacion.setCita(cita);
        cancelacion.setMotivo(request.getMotivo());
        cancelacion.setCanceladoPor(request.getCanceladoPor());
        cancelacion.setFechaCancelacion(LocalDateTime.now());
        cancelacion = cancelacionRepository.save(cancelacion);

        // Publicar evento de cita cancelada
        eventPublisher.publishAppointmentCancelled(
                paciente.email(),
                cita.getPacienteNombre(),
                cita.getMedicoId().toString(),
                cita.getMedicoNombre(),
                medico.especialidad(),
                cita.getFechaCita().toString(),
                cita.getHoraInicio().toString()
        );

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

    public CitaResponse atenderCitaHoy(UUID pacienteId) {
        LocalDate hoy = LocalDate.now();
        List<Cita> citas = citaRepository.findByPacienteIdOrderByFechaCitaDesc(pacienteId);
        Cita citaHoy = citas.stream()
                .filter(c -> hoy.equals(c.getFechaCita()) && "PROGRAMADA".equals(c.getEstado()))
                .findFirst()
                .orElse(null);

        if (citaHoy == null) {
            return null;
        }

        citaHoy.setEstado("ATENDIDA");
        citaHoy = citaRepository.save(citaHoy);
        return citaMapper.toResponse(citaHoy);
    }

    private PacienteResponse obtenerPaciente(UUID pacienteId) {
        String json = (String) rabbitTemplate.convertSendAndReceive(
                com.citas.service.config.RabbitMQConfig.CLINIC_EXCHANGE,
                com.citas.service.config.RabbitMQConfig.PACIENTE_REQUEST_ROUTING_KEY,
                pacienteId
        );
        if (json == null || "null".equals(json)) {
            throw new ResourceNotFoundException("Paciente no encontrado con id " + pacienteId);
        }
        try {
            return objectMapper.readValue(json, PacienteResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al mapear paciente", e);
        }
    }

    private MedicoResponse obtenerMedico(Long medicoId) {
        String json = (String) rabbitTemplate.convertSendAndReceive(
                com.citas.service.config.RabbitMQConfig.CLINIC_EXCHANGE,
                com.citas.service.config.RabbitMQConfig.MEDICO_REQUEST_ROUTING_KEY,
                medicoId
        );
        if (json == null || "null".equals(json)) {
            throw new ResourceNotFoundException("Medico no encontrado con id " + medicoId);
        }
        try {
            return objectMapper.readValue(json, MedicoResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al mapear medico", e);
        }
    }

    private boolean horariosSeSolapan(LocalTime inicioA, LocalTime finA, LocalTime inicioB, LocalTime finB) {
        return inicioA.isBefore(finB) && finA.isAfter(inicioB);
    }
}
