package com.service.consulta.consulta_service.service;

import com.service.consulta.consulta_service.repository.ConsultationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConsultationService {
    private final ConsultationRepository repository;

    public ConsultationService(ConsultationRepository repository) {
        this.repository = repository;
    }
    
}
