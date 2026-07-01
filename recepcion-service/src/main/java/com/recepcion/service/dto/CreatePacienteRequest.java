package com.recepcion.service.dto;

import com.recepcion.service.validation.DocumentoValido;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@DocumentoValido
public class CreatePacienteRequest {

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Pattern(regexp = "DNI|Carné de Extranjería|Pasaporte", message = "Tipo de documento no válido")
    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String nroDocumento;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(max = 100)
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno es obligatorio")
    @Size(max = 100)
    private String apellidoMaterno;

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 150)
    private String nombres;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento no puede ser futura")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El sexo es obligatorio")
    @Pattern(regexp = "Masculino|Femenino", message = "Sexo no válido")
    private String sexo;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener exactamente 9 dígitos")
    private String telefono;

    @Email(message = "Correo electrónico no válido")
    private String email;

    @Size(max = 300)
    private String direccion;

    @NotBlank(message = "La aseguradora es obligatoria")
    @Pattern(regexp = "SIS|EsSalud|EPS|Particular", message = "Aseguradora no válida")
    private String aseguradora;

    private String alergias;
}
