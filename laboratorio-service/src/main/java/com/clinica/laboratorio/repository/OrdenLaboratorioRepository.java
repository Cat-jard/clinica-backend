package com.clinica.laboratorio.repository;

import com.clinica.laboratorio.entity.OrdenLaboratorio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdenLaboratorioRepository extends JpaRepository<OrdenLaboratorio, UUID> {
    List<OrdenLaboratorio> findAllByOrderByPrioridadAscFechaSolicitudAsc();

    Optional<OrdenLaboratorio> findByNroOrden(String nroOrden);
}
