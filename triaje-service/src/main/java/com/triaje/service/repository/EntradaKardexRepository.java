package com.triaje.service.repository;

import com.triaje.service.entity.EntradaKardex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EntradaKardexRepository extends JpaRepository<EntradaKardex, UUID> {

    List<EntradaKardex> findByPacienteIdOrderByFechaHoraDesc(UUID pacienteId);

    boolean existsByPacienteIdAndFirmadoFalse(UUID pacienteId);
}
