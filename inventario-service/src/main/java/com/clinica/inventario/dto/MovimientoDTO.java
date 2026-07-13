package com.clinica.inventario.dto;

import com.clinica.inventario.enums.TipoMovimiento;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoDTO {

    private Long id;
    private Long productoId;
    private TipoMovimiento tipoMovimiento;
    private int cantidad;
    private LocalDateTime fechaMovimiento;
    private String usuario;
    private String motivo;
    private String observaciones;
}
