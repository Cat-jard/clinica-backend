package com.citas.service.dto;

public record AppointmentNotificationRequest(
        String patientEmail,
        String patientName,
        String doctorName,
        String specialty,
        String date,
        String time,
        String action // "CREAR", "CANCELAR"
) {}
