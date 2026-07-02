package com.recepcion.service.repository;

import com.recepcion.service.entity.ConsentimientoInformado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConsentimientoInformadoRepository extends JpaRepository<ConsentimientoInformado, UUID> {

    List<ConsentimientoInformado> findByPacienteIdOrderByCreatedAtDesc(UUID pacienteId);

    boolean existsByPacienteIdAndAceptadoTrue(UUID pacienteId);
}
