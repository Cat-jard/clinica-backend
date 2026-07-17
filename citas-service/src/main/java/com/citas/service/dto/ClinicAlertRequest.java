package com.citas.service.dto;

public record ClinicAlertRequest(
        String targetUserId,
        String title,
        String message,
        String severity // "INFO", "WARNING", "CRITICAL"
) {}
