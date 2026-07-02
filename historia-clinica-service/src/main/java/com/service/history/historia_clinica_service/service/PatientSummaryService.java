package com.service.history.historia_clinica_service.service;

import com.service.history.historia_clinica_service.domain.PatientSummary;
import com.service.history.historia_clinica_service.dtos.PatientSummaryRequest;
import com.service.history.historia_clinica_service.dtos.PatientSummaryResponse;
import com.service.history.historia_clinica_service.exception.ResourceNotFoundException;
import com.service.history.historia_clinica_service.mapper.PatientSummaryMapper;
import com.service.history.historia_clinica_service.repository.PatientSummaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PatientSummaryService {
    private final PatientSummaryRepository repository;
    private final PatientSummaryMapper mapper;

    public PatientSummaryService(PatientSummaryRepository repository,
                                 PatientSummaryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PatientSummaryResponse create(PatientSummaryRequest request) {
        PatientSummary patient = mapper.toEntity(request);
        patient = repository.save(patient);
        return mapper.toResponse(patient);
    }

    @Transactional(readOnly = true)
    public PatientSummaryResponse findById(UUID patientId) {

        PatientSummary patient = repository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient summary not found: " + patientId));

        return mapper.toResponse(patient);
    }

    @Transactional(readOnly = true)
    public List<PatientSummaryResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public PatientSummaryResponse update(UUID patientId,
                                         PatientSummaryRequest request) {

        PatientSummary patient = repository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient summary not found: " + patientId));

        mapper.updateEntity(patient, request);

        patient = repository.save(patient);

        return mapper.toResponse(patient);
    }

    public void delete(UUID patientId) {

        PatientSummary patient = repository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient summary not found: " + patientId));

        repository.delete(patient);
    }
}
