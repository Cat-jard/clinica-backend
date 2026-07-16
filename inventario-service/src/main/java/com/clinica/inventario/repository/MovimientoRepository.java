package com.clinica.inventario.repository;

import com.clinica.inventario.entity.Movimiento;
import com.clinica.inventario.enums.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByProductoIdOrderByFechaMovimientoDesc(Long productoId);

    List<Movimiento> findByProductoIdAndTipoMovimientoOrderByFechaMovimientoDesc(Long productoId, TipoMovimiento tipoMovimiento);
}
