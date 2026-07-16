package com.clinica.inventario.repository;

import com.clinica.inventario.entity.Producto;
import com.clinica.inventario.enums.EstadoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByCodigoProducto(String codigoProducto);

    boolean existsByCodigoProducto(String codigoProducto);

    List<Producto> findByEstado(EstadoInventario estado);

    @Query("SELECT p FROM Producto p WHERE p.cantidadDisponible < p.stockMinimo")
    List<Producto> findProductosConStockBajo();

    @Query("SELECT p FROM Producto p WHERE p.fechaVencimiento BETWEEN :inicio AND :fin")
    List<Producto> findProductosProximosAVencer(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    @Query("SELECT p FROM Producto p WHERE p.fechaVencimiento < :fecha")
    List<Producto> findProductosVencidos(@Param("fecha") LocalDate fecha);

    @Query("SELECT p.cantidadDisponible FROM Producto p WHERE p.id = :id")
    Optional<Integer> findCantidadDisponibleById(@Param("id") Long id);
}
