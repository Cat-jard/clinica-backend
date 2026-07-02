package com.service.history.historia_clinica_service.dtos.events_data;

public record AppointmentEventData(
        //These fields are based on the existing state of the frontend
        //All of these data is subject to change so don't blame me if it doesn't match
        //This is for JSON anyway so no biggie ._.xd
        String fecha,
        String hora,
        String medico,
        String especialidad,
        String motivo,
        String estado,
        boolean esFutura
) implements TimeLineEventData {
}
