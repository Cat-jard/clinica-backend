package com.service.consulta.consulta_service;

import com.service.consulta.consulta_service.domain.Consultation;
import com.service.consulta.consulta_service.domain.ConsultationStatus;
import com.service.consulta.consulta_service.domain.ConsultationType;
import com.service.consulta.consulta_service.dtos.ConsultationRequest;
import com.service.consulta.consulta_service.dtos.ConsultationResponse;
import com.service.consulta.consulta_service.dtos.ConsultationSummaryResponse;
import com.service.consulta.consulta_service.dtos.sections.AnamnesisDTO;
import com.service.consulta.consulta_service.dtos.sections.ClinicalPlanDTO;
import com.service.consulta.consulta_service.dtos.sections.PhysicalEvaluationDTO;
import com.service.consulta.consulta_service.dtos.sections.PrescriptionDTO;
import com.service.consulta.consulta_service.exception.ResourceNotFoundException;
import com.service.consulta.consulta_service.repository.ConsultationRepository;
import com.service.consulta.consulta_service.service.ConsultationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ConsultaServiceApplicationTests {
    @Autowired
    private ConsultationService service;

    @Autowired
    private ConsultationRepository repository;

    private ConsultationRequest createRequest() {
        return new ConsultationRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                ConsultationType.CONSULTA_INICIAL,
                ConsultationStatus.EN_CURSO,
                LocalDateTime.now(),
                null,
                new AnamnesisDTO(
                        "Dolor de cabeza",
                        "Hace dos días",
                        "Ninguno",
                        "Ninguno",
                        "Diabetes",
                        "No fuma"
                ),
                new PhysicalEvaluationDTO(
                        "Buen estado",
                        "Normal",
                        "Normal",
                        "Normal",
                        "Normal",
                        "Normal",
                        "Normal",
                        ""
                ),
                new ClinicalPlanDTO("Reposo por 3 días"),
                new PrescriptionDTO(List.of()),
                List.of(),
                List.of(),
                List.of(),
                List.of()
        );
    }

    @Test
    void contextLoads() {
    }

    @Test
    void shouldCreateConsultation() {
        ConsultationResponse response = service.create(createRequest());
        assertNotNull(response.id());
        assertEquals(ConsultationType.CONSULTA_INICIAL, response.consultationType());
        assertEquals(ConsultationStatus.EN_CURSO, response.status());
        assertEquals(1, repository.count());
    }

    @Test
    void shouldFindConsultationById() {
        ConsultationResponse created = service.create(createRequest());
        ConsultationResponse found = service.findById(created.id());
        assertEquals(created.id(), found.id());
    }

    @Test
    void shouldThrowWhenConsultationDoesNotExist() {
        UUID id = UUID.randomUUID();
        assertThrows(ResourceNotFoundException.class, () -> service.findById(id));
    }

    @Test
    void shouldReturnAllConsultations() {
        service.create(createRequest());
        service.create(createRequest());
        List<ConsultationSummaryResponse> list = service.findAll();
        assertEquals(2, list.size());
    }

    @Test
    void shouldFindByPatient() {
        ConsultationRequest request = createRequest();
        ConsultationResponse created = service.create(request);
        List<ConsultationSummaryResponse> list = service.findByPatient(created.patientId());
        assertEquals(1, list.size());
        assertEquals(created.patientId(), list.getFirst().patientId());
    }

    @Test
    void shouldFindByDoctor() {
        ConsultationRequest request = createRequest();
        ConsultationResponse created = service.create(request);
        List<ConsultationSummaryResponse> list = service.findByDoctor(created.doctorId());
        assertEquals(1, list.size());
    }

    @Test
    void shouldFindByAppointment() {
        ConsultationRequest request = createRequest();
        ConsultationResponse created = service.create(request);
        ConsultationResponse found = service.findByAppointment(created.appointmentId());
        assertEquals(created.id(), found.id());
    }

    @Test
    void shouldDeleteConsultation() {
        ConsultationResponse created = service.create(createRequest());
        service.delete(created.id());
        assertEquals(0, repository.count());
    }

    @Test
    void shouldUpdateConsultation() {
        ConsultationResponse created = service.create(createRequest());
        ConsultationRequest update = createRequest();
        ConsultationResponse response = service.update(created.id(), update);
        assertEquals(ConsultationType.CONSULTA_INICIAL, response.consultationType());
        assertEquals(ConsultationStatus.EN_CURSO, response.status());
        ConsultationRequest consultationUpdate = new ConsultationRequest(
                created.appointmentId(),
                created.patientId(),
                created.doctorId(),
                ConsultationType.CONSULTA_INICIAL,
                ConsultationStatus.FINALIZADA,
                created.startedAt(),
                LocalDateTime.now(),
                new AnamnesisDTO(
                        "Dolor de cabeza",
                        "Hace dos días",
                        "Ninguno",
                        "Ninguno",
                        "Diabetes",
                        "No fuma"
                ),
                new PhysicalEvaluationDTO(
                        "Buen estado",
                        "Normal",
                        "Normal",
                        "Normal",
                        "Normal",
                        "Normal",
                        "Normal",
                        ""
                ),
                new ClinicalPlanDTO("Reposo por 3 días"),
                new PrescriptionDTO(List.of()),
                List.of(),
                List.of(),
                List.of(),
                List.of()

        );
        response = service.update(created.id(), consultationUpdate);
        assertEquals(ConsultationStatus.FINALIZADA, response.status());
    }

    @Test
    void shouldPersistRelationships() {
        ConsultationResponse created = service.create(createRequest());
        Consultation consultation = repository.findById(created.id()).orElseThrow();
        assertNotNull(consultation.getAnamnesis());
        assertNotNull(consultation.getPhysicalEvaluation());
        assertNotNull(consultation.getClinicalPlan());
        assertNotNull(consultation.getPrescription());
        assertTrue(consultation.getDiagnoses().isEmpty());
        assertTrue(consultation.getCurrentMedicationList().isEmpty());
        assertTrue(consultation.getProcedures().isEmpty());
    }

}
