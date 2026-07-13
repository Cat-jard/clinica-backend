package com.service.history.historia_clinica_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.history.historia_clinica_service.domain.EventType;
import com.service.history.historia_clinica_service.domain.HistoryEvent;
import com.service.history.historia_clinica_service.domain.PatientSummary;
import com.service.history.historia_clinica_service.dtos.MedicalHistoryEventRequest;
import com.service.history.historia_clinica_service.dtos.TimelineEventResponse;
import com.service.history.historia_clinica_service.dtos.events.TriageCompletedEvent;
import com.service.history.historia_clinica_service.exception.ResourceNotFoundException;
import com.service.history.historia_clinica_service.mapper.HistoryEventMapper;
import com.service.history.historia_clinica_service.repository.HistoryEventRepository;
import com.service.history.historia_clinica_service.repository.PatientSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoryEventService {
    private final HistoryEventRepository repository;
    private final PatientSummaryRepository patientRepository;
    private final HistoryEventMapper mapper;
    private final ObjectMapper jsonMapper;

    @Transactional
    public TimelineEventResponse create(MedicalHistoryEventRequest request) {
        HistoryEvent entity = mapper.toEntity(request);
        entity = repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public TimelineEventResponse findById(UUID id) {

        HistoryEvent entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "History event not found: " + id));

        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<TimelineEventResponse> findByPatient(UUID patientId) {
        return repository.findByPatientIdOrderByOccurredAtDesc(patientId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TimelineEventResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public void delete(UUID id) {

        HistoryEvent entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "History event not found: " + id));

        repository.delete(entity);
    }

    @Transactional
    public void registerTriageEvent(TriageCompletedEvent event) {
        //Actualizar datos del paciente
        PatientSummary patientSummary = patientRepository.findById(event.patientId()).orElseThrow(() -> new ResourceNotFoundException("No se encontro el paciente"));
        patientSummary.setWeight(event.data().weight());
        patientSummary.setHeight(event.data().height());
        patientSummary.setSpo2(event.data().spo2());
        patientSummary.setDiastolicPressure(event.data().diastolicPressure());
        patientSummary.setSystolicPressure(event.data().systolicPressure());
        patientSummary.setUpdatedAt(LocalDateTime.now());
        patientRepository.save(patientSummary);
        //Guardar evento
        MedicalHistoryEventRequest request = new MedicalHistoryEventRequest(
                event.patientId(),
                EventType.TRIAJE,
                event.occurredAt(),
                "triaje-service",
                event.triageId(),
                event.data()
        );
        repository.save(mapper.toEntity(request));
    }
}
