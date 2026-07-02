package com.hospitalizacion.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "autorizaciones_ingreso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AutorizacionIngreso {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospitalizacion_id", nullable = false, unique = true,
                foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_autorizacion_hospitalizacion"))
    private Hospitalizacion hospitalizacion;

    @Column(name = "representante_nombre", length = 300)
    private String representanteNombre;

    @Column(name = "representante_dni", length = 20)
    private String representanteDni;

    @Column(name = "texto_legal", nullable = false, columnDefinition = "TEXT")
    private String textoLegal;

    @Column(name = "firma_base64", columnDefinition = "TEXT")
    private String firmaBase64;

    @Column(nullable = false)
    private Boolean firmado;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
