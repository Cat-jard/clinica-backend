package com.service.history.historia_clinica_service.dtos.events_data;

public record LaboratoryEventData(
        //Again, these fields are subject to change
        String fecha,
        String examen,
        String resultado,
        String valorRef,
        String unidad,
        String estado,
        String criticidad
) implements TimeLineEventData {
}
