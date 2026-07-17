package com.clinica.notificacion.dto;

public record ClinicAlertRequest(
        String targetUserId,
        String title,
        String message,
        String severity // "INFO", "WARNING", "CRITICAL"
) {}
