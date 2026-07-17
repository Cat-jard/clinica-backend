package com.historiaclinica.service.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AnamnesisDTO {
    private String motivoConsulta;
    private String enfermedadActual;
    private List<String> antecedentesPatologicos;
    private String antecedentesQuirurgicos;
    private String antecedentesAlergicos;
    private String antecedentesFamiliares;
    private List<String> habitos;
    private String medicacionActual;
}
