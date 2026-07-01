package com.citas.service.repository;

import com.citas.service.entity.Cita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CitaRepository extends JpaRepository<Cita, UUID> {

    List<Cita> findByPacienteIdOrderByFechaCitaDesc(UUID pacienteId);

    Page<Cita> findByMedicoIdOrderByFechaCitaDesc(Long medicoId, Pageable pageable);

    @Query("SELECT c FROM Cita c WHERE c.medicoId = :medicoId AND c.fechaCita = :fecha AND c.estado <> 'CANCELADA'")
    List<Cita> findOcupadasByMedicoAndFecha(@Param("medicoId") Long medicoId, @Param("fecha") LocalDate fecha);

    @Query("SELECT c FROM Cita c WHERE c.fechaCita BETWEEN :desde AND :hasta AND c.estado <> 'CANCELADA'")
    List<Cita> findPorRangoFechas(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

    long countByEstado(String estado);
}
