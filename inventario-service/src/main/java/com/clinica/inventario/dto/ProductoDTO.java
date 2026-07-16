package com.clinica.inventario.dto;

import com.clinica.inventario.enums.CategoriaMedicamento;
import com.clinica.inventario.enums.EstadoInventario;
import com.clinica.inventario.enums.Moneda;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {

    private Long id;
    private String codigoProducto;
    private String nombre;
    private String descripcion;
    private CategoriaMedicamento categoria;
    private String presentacion;
    private String concentracion;
    private String laboratorio;
    private boolean requiereReceta;
    private Long proveedorId;
    private Long categoriaId;
    private int cantidadDisponible;
    private int stockMinimo;
    private Integer stockMaximo;
    private String ubicacion;
    private String lote;
    private LocalDate fechaVencimiento;
    private LocalDate fechaFabricacion;
    private BigDecimal precioUnitario;
    private BigDecimal costoUnitario;
    private Moneda moneda;
    private BigDecimal iva;
    private EstadoInventario estado;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
    private String creadoPor;
    private String actualizadoPor;
}
