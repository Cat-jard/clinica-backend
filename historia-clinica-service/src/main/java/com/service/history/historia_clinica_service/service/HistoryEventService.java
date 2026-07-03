package com.service.history.historia_clinica_service.service;

import com.service.history.historia_clinica_service.domain.HistoryEvent;
import com.service.history.historia_clinica_service.dtos.MedicalHistoryEventRequest;
import com.service.history.historia_clinica_service.dtos.TimelineEventResponse;
import com.service.history.historia_clinica_service.exception.ResourceNotFoundException;
import com.service.history.historia_clinica_service.mapper.HistoryEventMapper;
import com.service.history.historia_clinica_service.repository.HistoryEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class HistoryEventService {
    private final HistoryEventRepository repository;
    private final HistoryEventMapper mapper;

    public HistoryEventService(HistoryEventRepository repository, HistoryEventMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

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

    public void delete(UUID id) {

        HistoryEvent entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "History event not found: " + id));

        repository.delete(entity);
    }
}
