package com.clinica.auditoria.repository;

import com.clinica.auditoria.domain.AccionAuditoria;
import com.clinica.auditoria.domain.RegistroAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistroAuditoriaRepository extends JpaRepository<RegistroAuditoria, Long> {

    long countByAccion(AccionAuditoria accion);

    /**
     * Listado con filtros opcionales por accion, modulo y texto libre
     * (usuario, descripcion o entidad). Cualquier parametro nulo se ignora.
     */
    @Query("""
            SELECT r FROM RegistroAuditoria r
            WHERE (:accion IS NULL OR r.accion = :accion)
              AND (:modulo IS NULL OR LOWER(r.modulo) = LOWER(CAST(:modulo AS string)))
              AND (:texto IS NULL OR
                   LOWER(r.usuarioNombre) LIKE LOWER(CONCAT('%', CAST(:texto AS string), '%')) OR
                   LOWER(r.usuarioEmail)  LIKE LOWER(CONCAT('%', CAST(:texto AS string), '%')) OR
                   LOWER(r.descripcion)   LIKE LOWER(CONCAT('%', CAST(:texto AS string), '%')) OR
                   LOWER(r.entidad)       LIKE LOWER(CONCAT('%', CAST(:texto AS string), '%')))
            ORDER BY r.fecha DESC
            """)
    List<RegistroAuditoria> buscar(@Param("accion") AccionAuditoria accion,
                                   @Param("modulo") String modulo,
                                   @Param("texto") String texto);
}
