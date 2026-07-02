package com.hospitalizacion.service.repository;

import com.hospitalizacion.service.entity.AutorizacionIngreso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AutorizacionIngresoRepository extends JpaRepository<AutorizacionIngreso, UUID> {

    Optional<AutorizacionIngreso> findByHospitalizacionId(UUID hospitalizacionId);

    boolean existsByHospitalizacionId(UUID hospitalizacionId);
}
