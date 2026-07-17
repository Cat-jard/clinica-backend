package com.historiaclinica.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamenFisicoDTO {
    private String examenGeneral;
    private String cabezaCuello;
    private String toraxPulmones;
    private String corazon;
    private String abdomen;
    private String extremidades;
    private String neurologico;
    private String otros;
}
