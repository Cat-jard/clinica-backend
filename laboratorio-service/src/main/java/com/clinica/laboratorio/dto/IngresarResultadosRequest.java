package com.clinica.laboratorio.dto;

import java.util.List;

public record IngresarResultadosRequest(List<ResultadoItemRequest> resultados) {
}
