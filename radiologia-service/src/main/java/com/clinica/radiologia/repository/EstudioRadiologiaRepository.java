package com.clinica.radiologia.repository;

import com.clinica.radiologia.entity.EstudioRadiologia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstudioRadiologiaRepository extends JpaRepository<EstudioRadiologia, UUID> {
    List<EstudioRadiologia> findAllByOrderByPrioridadAscFechaSolicitudAsc();

    Optional<EstudioRadiologia> findByNroOrden(String nroOrden);
}
