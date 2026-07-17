package com.historiaclinica.service.repository;

import com.historiaclinica.service.entity.AtencionMedica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AtencionMedicaRepository extends JpaRepository<AtencionMedica, UUID> {

    List<AtencionMedica> findByPacienteIdOrderByFechaAtencionDesc(UUID pacienteId);

    List<AtencionMedica> findByMedicoIdAndFechaAtencionOrderByHoraInicioAsc(Long medicoId, LocalDate fechaAtencion);

    java.util.Optional<AtencionMedica> findFirstByPacienteIdAndMedicoIdAndFechaAtencionAndEstadoOrderByHoraInicioDesc(
            UUID pacienteId, Long medicoId, LocalDate fechaAtencion, String estado);
}
