package com.clinica.inventario.service;

import com.clinica.inventario.entity.Movimiento;
import com.clinica.inventario.enums.TipoMovimiento;
import com.clinica.inventario.exception.ResourceNotFoundException;
import com.clinica.inventario.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;

    @Transactional(readOnly = true)
    public List<Movimiento> listarPorProducto(Long productoId) {
        return movimientoRepository.findByProductoIdOrderByFechaMovimientoDesc(productoId);
    }

    @Transactional(readOnly = true)
    public List<Movimiento> listarPorProductoYTipo(Long productoId, TipoMovimiento tipo) {
        return movimientoRepository.findByProductoIdAndTipoMovimientoOrderByFechaMovimientoDesc(productoId, tipo);
    }

    @Transactional(readOnly = true)
    public Movimiento obtenerPorId(Long id) {
        return movimientoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Movimiento", id));
    }
}
