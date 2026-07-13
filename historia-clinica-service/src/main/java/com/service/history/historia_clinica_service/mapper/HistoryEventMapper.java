package com.service.history.historia_clinica_service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.history.historia_clinica_service.domain.HistoryEvent;
import com.service.history.historia_clinica_service.dtos.MedicalHistoryEventRequest;
import com.service.history.historia_clinica_service.dtos.TimelineEventResponse;
import com.service.history.historia_clinica_service.dtos.events_data.*;
import org.springframework.stereotype.Component;

@Component
public class HistoryEventMapper {
    private final ObjectMapper objectMapper;

    public HistoryEventMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public HistoryEvent toEntity(MedicalHistoryEventRequest dto) {
        if (dto == null) {
            return null;
        }

        HistoryEvent event = new HistoryEvent();
        event.setPatientId(dto.patientId());
        event.setEventType(dto.type());
        event.setOccurredAt(dto.occurredAt());
        event.setSourceService(dto.sourceService());
        event.setReferenceId(dto.referenceId());
        event.setProjection(objectMapper.valueToTree(dto.projection()));

        return event;
    }

    public TimelineEventResponse toResponse(HistoryEvent entity) {
        return new TimelineEventResponse(
                entity.getId(),
                entity.getEventType(),
                entity.getOccurredAt(),
                entity.getSourceService(),
                mapProjection(entity)
        );
    }

    private TimeLineEventData mapProjection(HistoryEvent entity) {
        return switch (entity.getEventType()) {
            case CITA -> objectMapper.convertValue(
                    entity.getProjection(),
                    AppointmentEventData.class
            );
            case TRIAJE -> objectMapper.convertValue(
                    entity.getProjection(),
                    TriageEventData.class
            );
            case LABORATORIO -> objectMapper.convertValue(
                    entity.getProjection(),
                    LaboratoryEventData.class
            );

            case RADIOLOGIA -> objectMapper.convertValue(
                    entity.getProjection(),
                    RadiologyEventData.class
            );

            case CIRUGIA -> objectMapper.convertValue(
                    entity.getProjection(),
                    SurgeryEventData.class
            );
        };
    }

}
