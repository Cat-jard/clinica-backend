package com.triaje.service.repository;

import com.triaje.service.entity.ObservacionPaciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ObservacionPacienteRepository extends JpaRepository<ObservacionPaciente, UUID> {

    List<ObservacionPaciente> findByEstadoOrderByHoraIngresoDesc(String estado);

    boolean existsByPacienteIdAndEstado(UUID pacienteId, String estado);
}
