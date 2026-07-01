package com.recepcion.service.validation;

import com.recepcion.service.dto.CreatePacienteRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentoValidoValidator implements ConstraintValidator<DocumentoValido, CreatePacienteRequest> {

    @Override
    public boolean isValid(CreatePacienteRequest request, ConstraintValidatorContext context) {
        if (request == null) return true;

        if ("DNI".equals(request.getTipoDocumento())) {
            if (request.getNroDocumento() == null) return false;
            return request.getNroDocumento().matches("\\d{8}");
        }
        return true;
    }
}
