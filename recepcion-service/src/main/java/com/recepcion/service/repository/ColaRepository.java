package com.recepcion.service.repository;

import com.recepcion.service.entity.Cola;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ColaRepository extends JpaRepository<Cola, UUID> {

    List<Cola> findByFechaOrderByTicketAsc(LocalDate fecha);

    List<Cola> findByFechaAndEstadoOrderByTicketAsc(LocalDate fecha, String estado);
}
