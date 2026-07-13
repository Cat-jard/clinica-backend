package com.service.consulta.consulta_service.service;

import com.service.consulta.consulta_service.domain.Consultation;
import com.service.consulta.consulta_service.dtos.ConsultationRequest;
import com.service.consulta.consulta_service.dtos.ConsultationResponse;
import com.service.consulta.consulta_service.dtos.ConsultationSummaryResponse;
import com.service.consulta.consulta_service.exception.ResourceNotFoundException;
import com.service.consulta.consulta_service.mapper.ConsultationMapper;
import com.service.consulta.consulta_service.rabbit.ConsultationCompletedEvent;
import com.service.consulta.consulta_service.rabbit.ConsultationEventData;
import com.service.consulta.consulta_service.rabbit.Publisher;
import com.service.consulta.consulta_service.repository.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ConsultationService {
    private final ConsultationRepository repository;
    private final Publisher publisher;
    private final ConsultationMapper mapper;

    @Transactional
    public ConsultationResponse create(ConsultationRequest request) {
        Consultation consultation = mapper.toEntity(request);
        Consultation saved = repository.save(consultation);
        publisher.publish(mapper.toEvent(saved));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ConsultationResponse findById(UUID id) {
        Consultation consultation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found: " + id));
        return mapper.toResponse(consultation);
    }

    @Transactional(readOnly = true)
    public List<ConsultationSummaryResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toSummaryResponse)
                .toList();

    }

    @Transactional(readOnly = true)
    public List<ConsultationSummaryResponse> findByPatient(UUID patientId) {
        return repository.findByPatientId(patientId)
                .stream()
                .map(mapper::toSummaryResponse)
                .toList();

    }

    @Transactional(readOnly = true)
    public List<ConsultationSummaryResponse> findByDoctor(UUID doctorId) {
        return repository.findByDoctorId(doctorId)
                .stream()
                .map(mapper::toSummaryResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ConsultationResponse findByAppointment(UUID appointmentId) {
        Consultation consultation = repository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found for appointment: " + appointmentId));
        return mapper.toResponse(consultation);

    }

    public void delete(UUID id) {
        Consultation consultation = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Consultation not found: " + id));
        repository.delete(consultation);
    }

    public ConsultationResponse update(UUID id, ConsultationRequest request) {
        Consultation consultation = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Consultation not found: " + id));
        mapper.updateEntity(consultation, request);
        return mapper.toResponse(repository.save(consultation));
    }
}
