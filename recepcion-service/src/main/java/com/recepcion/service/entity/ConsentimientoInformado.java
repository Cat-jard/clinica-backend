package com.recepcion.service.entity;

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
@Table(name = "consentimientos_informados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ConsentimientoInformado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_consentimiento_paciente"))
    private Paciente paciente;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(name = "texto_legal", nullable = false, columnDefinition = "TEXT")
    private String textoLegal;

    @Column(name = "texto_legal_hash", nullable = false, length = 64)
    private String textoLegalHash;

    @Column(name = "version_texto", nullable = false, length = 10)
    private String versionTexto;

    @Column(name = "firma_base64", columnDefinition = "TEXT")
    private String firmaBase64;

    @Column(name = "firma_hash", length = 64)
    private String firmaHash;

    @Column(nullable = false)
    private Boolean aceptado = false;

    @Column(name = "fecha_exposicion", nullable = false)
    private LocalDateTime fechaExposicion;

    @Column(name = "fecha_firma")
    private LocalDateTime fechaFirma;

    @Column(name = "ip_origen", nullable = false, length = 45)
    private String ipOrigen;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
