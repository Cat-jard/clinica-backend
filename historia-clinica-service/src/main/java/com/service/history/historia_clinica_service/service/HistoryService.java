package com.service.history.historia_clinica_service.service;

import com.service.history.historia_clinica_service.repository.ClinicalHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HistoryService {
    private final ClinicalHistoryRepository clinicalHistoryRepository;

    public HistoryService(ClinicalHistoryRepository clinicalHistoryRepository) {
        this.clinicalHistoryRepository = clinicalHistoryRepository;
    }
}
