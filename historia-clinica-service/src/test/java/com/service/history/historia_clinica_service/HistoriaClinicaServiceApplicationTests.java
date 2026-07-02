package com.service.history.historia_clinica_service;

import com.service.history.historia_clinica_service.domain.EventType;
import com.service.history.historia_clinica_service.dtos.MedicalHistoryEventRequest;
import com.service.history.historia_clinica_service.dtos.TimelineEventResponse;
import com.service.history.historia_clinica_service.dtos.events_data.AppointmentEventData;
import com.service.history.historia_clinica_service.exception.ResourceNotFoundException;
import com.service.history.historia_clinica_service.service.HistoryEventService;
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
class HistoriaClinicaServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private HistoryEventService service;

    @Test
    void shouldCreateHistoryEvent() {

        MedicalHistoryEventRequest request =
                new MedicalHistoryEventRequest(
                        UUID.randomUUID(),
                        EventType.CITA,
                        LocalDateTime.now(),
                        "appointment-service",
                        UUID.randomUUID(),
                        new AppointmentEventData(
                                "2026-07-02",
                                "10:30",
                                "Dr. House",
                                "Neurología",
                                "Dolor de cabeza",
                                "PROGRAMADA",
                                true
                        )
                );

        TimelineEventResponse response = service.create(request);

        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals(EventType.CITA, response.type());
        assertEquals("appointment-service", response.sourceService());
    }

    @Test
    void shouldFindHistoryEventById() {

        MedicalHistoryEventRequest request =
                new MedicalHistoryEventRequest(
                        UUID.randomUUID(),
                        EventType.CITA,
                        LocalDateTime.now(),
                        "appointment-service",
                        UUID.randomUUID(),
                        new AppointmentEventData(
                                "2026-07-02",
                                "10:30",
                                "Dr. House",
                                "Neurología",
                                "Dolor",
                                "PROGRAMADA",
                                false
                        )
                );

        TimelineEventResponse created = service.create(request);

        TimelineEventResponse found =
                service.findById(created.id());

        assertEquals(created.id(), found.id());
        assertEquals(created.type(), found.type());
    }

    @Test
    void shouldFindAllHistoryEvents() {

        service.create(new MedicalHistoryEventRequest(
                UUID.randomUUID(),
                EventType.CITA,
                LocalDateTime.now(),
                "appointment-service",
                UUID.randomUUID(),
                new AppointmentEventData(
                        "2026-07-02",
                        "10:30",
                        "Doctor",
                        "Cardiología",
                        "",
                        "OK",
                        false
                )
        ));

        List<TimelineEventResponse> events =
                service.findAll();

        assertFalse(events.isEmpty());
    }

    @Test
    void shouldFindEventsByPatient() {

        UUID patient = UUID.randomUUID();

        service.create(new MedicalHistoryEventRequest(
                patient,
                EventType.CITA,
                LocalDateTime.now(),
                "appointment-service",
                UUID.randomUUID(),
                new AppointmentEventData(
                        "2026-07-02",
                        "10:30",
                        "Doctor",
                        "Cardiología",
                        "",
                        "OK",
                        false
                )
        ));

        List<TimelineEventResponse> events =
                service.findByPatient(patient);

        assertEquals(1, events.size());
    }

    @Test
    void shouldDeleteHistoryEvent() {

        TimelineEventResponse created =
                service.create(
                        new MedicalHistoryEventRequest(
                                UUID.randomUUID(),
                                EventType.CITA,
                                LocalDateTime.now(),
                                "appointment-service",
                                UUID.randomUUID(),
                                new AppointmentEventData(
                                        "2026-07-02",
                                        "10:30",
                                        "Doctor",
                                        "Cardiología",
                                        "",
                                        "OK",
                                        false
                                )
                        )
                );

        service.delete(created.id());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(created.id())
        );
    }

    @Test
    void shouldThrowWhenHistoryEventDoesNotExist() {

        UUID id = UUID.randomUUID();

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(id)
        );
    }

}
