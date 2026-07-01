package com.citas.service.repository;

import com.citas.service.entity.CancelacionCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CancelacionCitaRepository extends JpaRepository<CancelacionCita, UUID> {

    Optional<CancelacionCita> findByCitaId(UUID citaId);

    boolean existsByCitaId(UUID citaId);
}
