package com.clinica.inventario.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String recurso, Long id) {
        super(recurso + " no encontrado con id: " + id);
    }

    public ResourceNotFoundException(String recurso, String codigo) {
        super(recurso + " no encontrado con código: " + codigo);
    }
}
