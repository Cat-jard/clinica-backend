package com.triaje.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "entradas_kardex")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class EntradaKardex {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "paciente_id", nullable = false)
    private UUID pacienteId;

    @Column(name = "paciente_nombre", nullable = false, length = 300)
    private String pacienteNombre;

    @Column(name = "cita_id")
    private UUID citaId;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "pas_sistolica")
    private Integer pasSistolica;

    @Column(name = "pas_diastolica")
    private Integer pasDiastolica;

    @Column(name = "frec_cardiaca")
    private Integer frecCardiaca;

    @Column(name = "frec_respiratoria")
    private Integer frecRespiratoria;

    @Column(precision = 4, scale = 1)
    private BigDecimal temperatura;

    private Integer spo2;

    @Column(name = "ingresos_hidricos", nullable = false)
    private Integer ingresosHidricos;

    @Column(name = "egresos_hidricos", nullable = false)
    private Integer egresosHidricos;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String evolucion;

    @Column(nullable = false)
    private Boolean firmado;

    @Column(name = "firmado_por", length = 100)
    private String firmadoPor;

    @Column(name = "firma_base64", columnDefinition = "TEXT")
    private String firmaBase64;

    @Column(name = "firma_hash", length = 64)
    private String firmaHash;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "entradaKardex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicamentoKardex> medicamentos = new ArrayList<>();
}
