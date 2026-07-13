package com.service.history.historia_clinica_service.repository;

import com.service.history.historia_clinica_service.domain.HistoryEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryEventRepository extends JpaRepository<HistoryEvent, UUID> {
    List<HistoryEvent> findByPatientIdOrderByOccurredAtDesc(UUID patientId);
}
