package com.clinica.inventario.dto;

import com.clinica.inventario.enums.CategoriaMedicamento;
import com.clinica.inventario.enums.EstadoInventario;
import com.clinica.inventario.enums.Moneda;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoRequestDTO {

    @NotBlank(message = "El código del producto es obligatorio")
    private String codigoProducto;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    private CategoriaMedicamento categoria;

    @NotBlank(message = "La presentación es obligatoria")
    private String presentacion;

    private String concentracion;

    private String laboratorio;

    private boolean requiereReceta;

    private Long proveedorId;

    private Long categoriaId;

    @Min(value = 0, message = "La cantidad disponible no puede ser negativa")
    private int cantidadDisponible;

    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private int stockMinimo;

    private Integer stockMaximo;

    private String ubicacion;

    private String lote;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate fechaVencimiento;

    private LocalDate fechaFabricacion;

    @NotNull(message = "El precio unitario es obligatorio")
    private BigDecimal precioUnitario;

    private BigDecimal costoUnitario;

    private Moneda moneda;

    private BigDecimal iva;

    private EstadoInventario estado;

    private String creadoPor;
}
