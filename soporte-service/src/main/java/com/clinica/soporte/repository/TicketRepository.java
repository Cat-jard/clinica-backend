package com.clinica.soporte.repository;

import com.clinica.soporte.domain.EstadoTicket;
import com.clinica.soporte.domain.PrioridadTicket;
import com.clinica.soporte.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    boolean existsByCodigo(String codigo);

    long countByEstado(EstadoTicket estado);

    /**
     * Listado con filtros opcionales por estado, prioridad y texto libre
     * (codigo, titulo o solicitante). Cualquier parametro nulo se ignora.
     */
    @Query("""
            SELECT t FROM Ticket t
            WHERE (:estado IS NULL OR t.estado = :estado)
              AND (:prioridad IS NULL OR t.prioridad = :prioridad)
              AND (:texto IS NULL OR
                   LOWER(t.codigo)            LIKE LOWER(CONCAT('%', :texto, '%')) OR
                   LOWER(t.titulo)            LIKE LOWER(CONCAT('%', :texto, '%')) OR
                   LOWER(t.solicitanteNombre) LIKE LOWER(CONCAT('%', :texto, '%')))
            ORDER BY t.fechaCreacion DESC
            """)
    List<Ticket> buscar(@Param("estado") EstadoTicket estado,
                        @Param("prioridad") PrioridadTicket prioridad,
                        @Param("texto") String texto);
}
