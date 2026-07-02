package com.hospitalizacion.service.repository;

import com.hospitalizacion.service.entity.Hospitalizacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface HospitalizacionRepository extends JpaRepository<Hospitalizacion, UUID> {

    Optional<Hospitalizacion> findByPacienteIdAndEstado(UUID pacienteId, String estado);

    boolean existsByPacienteIdAndEstado(UUID pacienteId, String estado);

    long countByEstado(String estado);

    @Query("SELECT h FROM Hospitalizacion h WHERE (:estado IS NULL OR h.estado = :estado) " +
           "AND (:servicio IS NULL OR h.servicio = :servicio) ORDER BY h.fechaIngreso DESC")
    Page<Hospitalizacion> buscarConFiltros(@Param("estado") String estado,
                                            @Param("servicio") String servicio,
                                            Pageable pageable);

    long countByServicioAndEstado(String servicio, String estado);

    @Query("SELECT h.servicio, COUNT(h) FROM Hospitalizacion h WHERE h.estado = 'HOSPITALIZADO' GROUP BY h.servicio")
    java.util.List<Object[]> countHospitalizadosPorServicio();
}
