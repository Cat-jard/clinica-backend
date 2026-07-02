package com.service.history.historia_clinica_service.repository;

import com.service.history.historia_clinica_service.domain.HistoryEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClinicalHistoryRepository extends JpaRepository<HistoryEvent, UUID> {
}
