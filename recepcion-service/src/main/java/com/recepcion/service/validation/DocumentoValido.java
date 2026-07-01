package com.recepcion.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DocumentoValidoValidator.class)
@Documented
public @interface DocumentoValido {

    String message() default "El número de documento no es válido para el tipo seleccionado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
