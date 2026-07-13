package com.clinica.inventario.entity;

import com.clinica.inventario.enums.CategoriaMedicamento;
import com.clinica.inventario.enums.EstadoInventario;
import com.clinica.inventario.enums.Moneda;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventario", indexes = {
    @Index(name = "idx_codigo_producto", columnList = "codigoProducto"),
    @Index(name = "idx_fecha_vencimiento", columnList = "fechaVencimiento"),
    @Index(name = "idx_estado", columnList = "estado")
})
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String codigoProducto;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private CategoriaMedicamento categoria;

    @Column(nullable = false, length = 100)
    private String presentacion;

    @Column(length = 100)
    private String concentracion;

    @Column(length = 200)
    private String laboratorio;

    @Column(nullable = false)
    private boolean requiereReceta;

    private Long proveedorId;

    private Long categoriaId;

    @Min(0)
    @Column(nullable = false)
    private int cantidadDisponible;

    @Min(0)
    @Column(nullable = false)
    private int stockMinimo;

    private Integer stockMaximo;

    @Column(length = 100)
    private String ubicacion;

    @Column(length = 50)
    private String lote;

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    private LocalDate fechaFabricacion;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(precision = 10, scale = 2)
    private BigDecimal costoUnitario;

    @Enumerated(EnumType.STRING)
    @Column(length = 3)
    private Moneda moneda;

    @Column(precision = 5, scale = 2)
    private BigDecimal iva;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoInventario estado;

    @Column(updatable = false)
    private LocalDateTime creadoEn;

    private LocalDateTime actualizadoEn;

    @Column(length = 100)
    private String creadoPor;

    @Column(length = 100)
    private String actualizadoPor;

    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
        actualizadoEn = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoInventario.ACTIVO;
        }
        if (moneda == null) {
            moneda = Moneda.PEN;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        actualizadoEn = LocalDateTime.now();
    }
}
