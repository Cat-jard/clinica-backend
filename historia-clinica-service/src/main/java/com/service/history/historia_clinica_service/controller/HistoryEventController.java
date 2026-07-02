package com.service.history.historia_clinica_service.controller;

import com.service.history.historia_clinica_service.dtos.MedicalHistoryEventRequest;
import com.service.history.historia_clinica_service.dtos.TimelineEventResponse;
import com.service.history.historia_clinica_service.service.HistoryEventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/history-events")
public class HistoryEventController {
    private final HistoryEventService service;

    public HistoryEventController(HistoryEventService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TimelineEventResponse> create(@Valid @RequestBody MedicalHistoryEventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimelineEventResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<TimelineEventResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<TimelineEventResponse>> findByPatient(@PathVariable UUID patientId) {
        return ResponseEntity.ok(service.findByPatient(patientId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
