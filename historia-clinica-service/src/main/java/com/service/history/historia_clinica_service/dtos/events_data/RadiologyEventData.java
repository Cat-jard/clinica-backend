package com.service.history.historia_clinica_service.dtos.events_data;

public record RadiologyEventData(
        String tipoEstudio,
        String fecha,
        String hora,
        String medicoSolicitante,
        String modalidad,
        String conclusion
) implements TimeLineEventData {
}
