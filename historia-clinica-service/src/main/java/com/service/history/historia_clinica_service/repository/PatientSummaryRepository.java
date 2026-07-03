package com.service.history.historia_clinica_service.repository;

import com.service.history.historia_clinica_service.domain.PatientSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientSummaryRepository extends JpaRepository<PatientSummary, UUID> {
}
