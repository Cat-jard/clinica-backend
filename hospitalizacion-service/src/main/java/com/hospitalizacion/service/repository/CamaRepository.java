package com.hospitalizacion.service.repository;

import com.hospitalizacion.service.entity.Cama;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CamaRepository extends JpaRepository<Cama, UUID> {

    List<Cama> findByServicioAndEstado(String servicio, String estado);

    List<Cama> findByEstado(String estado);

    Optional<Cama> findByNumeroAndServicio(String numero, String servicio);

    long countByServicioAndEstado(String servicio, String estado);

    long countByEstado(String estado);
}
