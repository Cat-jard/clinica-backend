package com.clinica.soporte.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * Ticket de incidencia de Soporte / TI. Mapea a la tabla {@code tickets}.
 */
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Codigo legible para el usuario, p. ej. "TCK-0001". */
    @Column(nullable = false, unique = true, length = 12)
    private String codigo;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Column(name = "solicitante_dni", length = 8)
    private String solicitanteDni;

    @Column(name = "solicitante_nombre", nullable = false, length = 150)
    private String solicitanteNombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private CategoriaTicket categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private PrioridadTicket prioridad = PrioridadTicket.MEDIA;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private EstadoTicket estado = EstadoTicket.ABIERTO;

    /** Tecnico de Soporte responsable. Opcional hasta que se asigna. */
    @Column(name = "asignado_a", length = 150)
    private String asignadoA;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    void alCrear() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.estado == null) this.estado = EstadoTicket.ABIERTO;
        if (this.prioridad == null) this.prioridad = PrioridadTicket.MEDIA;
    }

    @PreUpdate
    void alActualizar() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    // ----- getters / setters -----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getSolicitanteDni() { return solicitanteDni; }
    public void setSolicitanteDni(String solicitanteDni) { this.solicitanteDni = solicitanteDni; }

    public String getSolicitanteNombre() { return solicitanteNombre; }
    public void setSolicitanteNombre(String solicitanteNombre) { this.solicitanteNombre = solicitanteNombre; }

    public CategoriaTicket getCategoria() { return categoria; }
    public void setCategoria(CategoriaTicket categoria) { this.categoria = categoria; }

    public PrioridadTicket getPrioridad() { return prioridad; }
    public void setPrioridad(PrioridadTicket prioridad) { this.prioridad = prioridad; }

    public EstadoTicket getEstado() { return estado; }
    public void setEstado(EstadoTicket estado) { this.estado = estado; }

    public String getAsignadoA() { return asignadoA; }
    public void setAsignadoA(String asignadoA) { this.asignadoA = asignadoA; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
