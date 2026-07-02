package com.hospitalizacion.service.feign.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponseWrapper<T> {
    private T data;
    private String message;
    private String timestamp;
}
