package com.clinica.usuario.repository;

import com.clinica.usuario.domain.EstadoUsuario;
import com.clinica.usuario.domain.Rol;
import com.clinica.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmailIgnoreCase(String email);

    Optional<Usuario> findByDni(String dni);

    boolean existsByDni(String dni);

    boolean existsByEmailIgnoreCase(String email);

    long countByEstado(EstadoUsuario estado);

    /**
     * Listado con filtros opcionales por rol y por texto libre
     * (nombre, apellidos, dni o email). Cualquier parametro nulo se ignora.
     */
    @Query("""
            SELECT u FROM Usuario u
            WHERE (:rol IS NULL OR u.rol = :rol)
              AND (:texto IS NULL OR
                   LOWER(u.nombre)    LIKE LOWER(CONCAT('%', :texto, '%')) OR
                   LOWER(u.apellidos) LIKE LOWER(CONCAT('%', :texto, '%')) OR
                   u.dni   LIKE CONCAT('%', :texto, '%') OR
                   LOWER(u.email)     LIKE LOWER(CONCAT('%', :texto, '%')))
            ORDER BY u.fechaCreacion DESC
            """)
    List<Usuario> buscar(@Param("rol") Rol rol, @Param("texto") String texto);
}
