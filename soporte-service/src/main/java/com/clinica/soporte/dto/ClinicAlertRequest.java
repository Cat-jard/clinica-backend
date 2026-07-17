package com.clinica.soporte.dto;

public record ClinicAlertRequest(
        String targetUserId,
        String title,
        String message,
        String severity // "INFO", "WARNING", "CRITICAL"
) {}
