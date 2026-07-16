package com.triaje.service.repository;

import com.triaje.service.entity.RegistroTriaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface RegistroTriajeRepository extends JpaRepository<RegistroTriaje, UUID> {

    Optional<RegistroTriaje> findTopByPacienteIdOrderByTimestampDesc(UUID pacienteId);

    Page<RegistroTriaje> findByFechaTriajeOrderByTimestampDesc(LocalDate fechaTriaje, Pageable pageable);

    Page<RegistroTriaje> findByFechaTriajeAndPrioridadOrderByTimestampDesc(LocalDate fecha, String prioridad, Pageable pageable);

    Page<RegistroTriaje> findByPrioridadOrderByTimestampDesc(String prioridad, Pageable pageable);

    long countByFechaTriaje(LocalDate fecha);

    @Query("SELECT COUNT(r) FROM RegistroTriaje r WHERE r.fechaTriaje = :fecha AND r.prioridad = :prioridad")
    long countByFechaAndPrioridad(@Param("fecha") LocalDate fecha, @Param("prioridad") String prioridad);

    @Query("SELECT r.prioridad, COUNT(r) FROM RegistroTriaje r WHERE r.fechaTriaje = :fecha GROUP BY r.prioridad")
    java.util.List<Object[]> countGroupByPrioridad(@Param("fecha") LocalDate fecha);

    @Query("SELECT FUNCTION('DATE_TRUNC', 'hour', r.timestamp), COUNT(r) FROM RegistroTriaje r " +
           "WHERE r.fechaTriaje = :fecha GROUP BY FUNCTION('DATE_TRUNC', 'hour', r.timestamp) ORDER BY 1")
    java.util.List<Object[]> countLlegadasPorHora(@Param("fecha") LocalDate fecha);

    @Query("SELECT r.motivoConsulta, COUNT(r) as cnt FROM RegistroTriaje r " +
           "WHERE r.fechaTriaje = :fecha GROUP BY r.motivoConsulta ORDER BY cnt DESC")
    java.util.List<Object[]> topMotivos(@Param("fecha") LocalDate fecha, Pageable pageable);

    @Query("SELECT AVG(s.spo2) FROM RegistroTriaje r JOIN r.signosVitales s WHERE r.fechaTriaje = :fecha")
    Double avgSpO2ByFecha(@Param("fecha") LocalDate fecha);
}
