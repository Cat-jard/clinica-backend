package com.hospitalizacion.service.repository;

import com.hospitalizacion.service.entity.Epicrisis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EpicrisisRepository extends JpaRepository<Epicrisis, UUID> {

    Optional<Epicrisis> findByHospitalizacionId(UUID hospitalizacionId);

    boolean existsByHospitalizacionId(UUID hospitalizacionId);
}
