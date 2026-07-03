package com.service.history.historia_clinica_service;

import com.service.history.historia_clinica_service.domain.SexType;
import com.service.history.historia_clinica_service.dtos.PatientSummaryRequest;
import com.service.history.historia_clinica_service.dtos.PatientSummaryResponse;
import com.service.history.historia_clinica_service.exception.ResourceNotFoundException;
import com.service.history.historia_clinica_service.service.PatientSummaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PatientSummaryTest {

    @Test
    void contextLoads() {
    }

    @Autowired
    private PatientSummaryService service;

    private PatientSummaryRequest request() {

        return new PatientSummaryRequest(
                UUID.randomUUID(),
                "Juan Perez",
                "12345678",
                (short) 30,
                SexType.MASCULINO,
                (short) 120,
                (short) 80,
                (short) 98,
                new BigDecimal("72.50"),
                new BigDecimal("1.75"),
                List.of("Penicilina")
        );
    }

    @Test
    void shouldCreatePatientSummary() {

        PatientSummaryResponse response =
                service.create(request());

        assertNotNull(response.patientId());
        assertEquals("Juan Perez", response.fullname());
        assertEquals("12345678", response.dni());
    }

    @Test
    void shouldFindPatientSummaryById() {

        PatientSummaryResponse created =
                service.create(request());

        PatientSummaryResponse found =
                service.findById(created.patientId());

        assertEquals(created.patientId(), found.patientId());
    }

    @Test
    void shouldFindAllPatientSummaries() {

        service.create(request());

        List<PatientSummaryResponse> patients =
                service.findAll();

        assertFalse(patients.isEmpty());
    }

    @Test
    void shouldUpdatePatientSummary() {

        PatientSummaryResponse created =
                service.create(request());

        PatientSummaryRequest update =
                new PatientSummaryRequest(
                        created.patientId(),
                        "Juan Carlos Perez",
                        "12345678",
                        (short) 31,
                        SexType.MASCULINO,
                        (short) 130,
                        (short) 85,
                        (short) 97,
                        new BigDecimal("75.00"),
                        new BigDecimal("1.75"),
                        List.of(
                                "Penicilina",
                                "Mariscos"
                        )
                );

        PatientSummaryResponse response =
                service.update(
                        created.patientId(),
                        update
                );

        assertEquals("Juan Carlos Perez", response.fullname());
        assertEquals((short) 31, response.age());
        assertEquals(2, response.allergies().size());
    }

    @Test
    void shouldDeletePatientSummary() {

        PatientSummaryResponse created =
                service.create(request());

        service.delete(created.patientId());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(created.patientId())
        );
    }

    @Test
    void shouldThrowWhenPatientDoesNotExist() {

        UUID id = UUID.randomUUID();

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(id)
        );
    }
}
