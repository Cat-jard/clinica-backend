package com.triaje.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "medicamentos_kardex")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoKardex {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrada_kardex_id", nullable = false,
                foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_medicamento_entrada_kardex"))
    private EntradaKardex entradaKardex;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String dosis;

    @Column(nullable = false, length = 50)
    private String via;

    @Column(nullable = false, length = 10)
    private String hora;
}
