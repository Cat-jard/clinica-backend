package com.service.history.historia_clinica_service.dtos.events_data;

public sealed interface TimeLineEventData permits AppointmentEventData, LaboratoryEventData, RadiologyEventData, SurgeryEventData, TriageEventData {
}
