package com.service.consulta.consulta_service.controller;

import com.service.consulta.consulta_service.dtos.ConsultationRequest;
import com.service.consulta.consulta_service.dtos.ConsultationResponse;
import com.service.consulta.consulta_service.dtos.ConsultationSummaryResponse;
import com.service.consulta.consulta_service.service.ConsultationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/consultation")
public class ConsultationController {
    private final ConsultationService service;

    public ConsultationController(ConsultationService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<ConsultationResponse> create(@Valid @RequestBody ConsultationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponse> searchById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ConsultationSummaryResponse>> getAllConsultationSummaries() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/patient/{userId}")
    public ResponseEntity<List<ConsultationSummaryResponse>> getConsultationByPatiendId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findByPatient(id));
    }

    @GetMapping("/doctor/{userId}")
    public ResponseEntity<List<ConsultationSummaryResponse>> getConsultationByDoctorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findByDoctor(id));
    }

    @GetMapping("/appointment/{userId}")
    public ResponseEntity<ConsultationResponse> getConsultationByAppointmentId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findByAppointment(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ConsultationResponse> updateConsultation(@PathVariable UUID id, @Valid @RequestBody ConsultationRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }
}
