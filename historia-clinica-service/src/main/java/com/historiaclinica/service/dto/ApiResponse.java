package com.historiaclinica.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(T data, String message, String timestamp) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data, "OK", Instant.now().toString());
    }

    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>(data, message, Instant.now().toString());
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, message, Instant.now().toString());
    }
}
