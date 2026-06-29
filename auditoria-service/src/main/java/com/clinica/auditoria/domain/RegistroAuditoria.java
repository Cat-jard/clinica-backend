package com.clinica.auditoria.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * Evento de auditoria. Mapea a la tabla {@code registros_auditoria}.
 *
 * <p>Regla de oro: un registro de auditoria es <b>inmutable</b>; solo se crea y
 * se consulta, nunca se modifica ni se elimina (trazabilidad inalterable).
 */
@Entity
@Table(name = "registros_auditoria")
public class RegistroAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_email", length = 150)
    private String usuarioEmail;

    @Column(name = "usuario_nombre", length = 150)
    private String usuarioNombre;

    @Column(length = 20)
    private String rol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccionAuditoria accion;

    @Column(nullable = false, length = 60)
    private String modulo;

    @Column(nullable = false, length = 300)
    private String descripcion;

    /** Recurso afectado (p. ej. "Usuario #12", "Cita #340"). Opcional. */
    @Column(length = 120)
    private String entidad;

    @Column(length = 45)
    private String ip;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha;

    @PrePersist
    void alCrear() {
        if (this.fecha == null) {
            this.fecha = LocalDateTime.now();
        }
    }

    // ----- getters / setters -----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsuarioEmail() { return usuarioEmail; }
    public void setUsuarioEmail(String usuarioEmail) { this.usuarioEmail = usuarioEmail; }

    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public AccionAuditoria getAccion() { return accion; }
    public void setAccion(AccionAuditoria accion) { this.accion = accion; }

    public String getModulo() { return modulo; }
    public void setModulo(String modulo) { this.modulo = modulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEntidad() { return entidad; }
    public void setEntidad(String entidad) { this.entidad = entidad; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
