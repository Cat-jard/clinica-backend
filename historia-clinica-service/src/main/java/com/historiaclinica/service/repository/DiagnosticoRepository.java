package com.historiaclinica.service.repository;

import com.historiaclinica.service.entity.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, UUID> {

    List<Diagnostico> findByAtencionIdOrderByOrdenAsc(UUID atencionId);
}
