package com.service.history.historia_clinica_service.controller;

import com.service.history.historia_clinica_service.dtos.PatientSummaryRequest;
import com.service.history.historia_clinica_service.dtos.PatientSummaryResponse;
import com.service.history.historia_clinica_service.service.PatientSummaryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patient-summaries")
public class PatientSummaryController {
    private final PatientSummaryService service;

    public PatientSummaryController(PatientSummaryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PatientSummaryResponse> create(@Valid @RequestBody PatientSummaryRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientSummaryResponse> findById(@PathVariable UUID patientId) {
        return ResponseEntity.ok(service.findById(patientId));
    }

    @GetMapping
    public ResponseEntity<List<PatientSummaryResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientSummaryResponse> update(@PathVariable UUID patientId, @Valid @RequestBody PatientSummaryRequest request) {
        return ResponseEntity.ok(service.update(patientId, request));
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> delete(@PathVariable UUID patientId) {
        service.delete(patientId);
        return ResponseEntity.noContent().build();
    }
}
