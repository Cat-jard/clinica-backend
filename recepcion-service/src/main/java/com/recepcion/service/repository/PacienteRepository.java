package com.recepcion.service.repository;

import com.recepcion.service.entity.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {

    boolean existsByNroDocumento(String nroDocumento);

    Optional<Paciente> findByNroDocumento(String nroDocumento);

    @Query(value = "SELECT p.nro_historia FROM pacientes p WHERE p.nro_historia LIKE :prefix ORDER BY p.nro_historia DESC LIMIT 1", nativeQuery = true)
    Optional<String> findLastNroHistoriaByPrefix(@Param("prefix") String prefix);

    @Query("SELECT p FROM Paciente p WHERE " +
           "(:q IS NULL OR LOWER(p.nroDocumento) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.nombres) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.apellidoPaterno) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.apellidoMaterno) LIKE LOWER(CONCAT('%', :q, '%')))")
    Page<Paciente> search(@Param("q") String q, Pageable pageable);
}
