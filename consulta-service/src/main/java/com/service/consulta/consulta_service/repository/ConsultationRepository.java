package com.service.consulta.consulta_service.repository;

import com.service.consulta.consulta_service.domain.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultationRepository extends JpaRepository<Consultation, UUID> {
    List<Consultation> findByPatientId(UUID patientId);

    List<Consultation> findByDoctorId(UUID doctorId);

    Optional<Consultation> findByAppointmentId(UUID appointmentId);
}
