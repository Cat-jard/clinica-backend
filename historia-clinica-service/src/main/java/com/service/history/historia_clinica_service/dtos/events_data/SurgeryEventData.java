package com.service.history.historia_clinica_service.dtos.events_data;

public record SurgeryEventData(
        String tipo,
        String fecha,
        String hora,
        String medico,
        String resultado
) implements TimeLineEventData {
}
