package com.clinica.inventario.service;

import com.clinica.inventario.dto.StockRequestDTO;
import com.clinica.inventario.entity.Movimiento;
import com.clinica.inventario.entity.Producto;
import com.clinica.inventario.enums.EstadoInventario;
import com.clinica.inventario.enums.TipoMovimiento;
import com.clinica.inventario.exception.ProductoVencidoException;
import com.clinica.inventario.exception.ResourceNotFoundException;
import com.clinica.inventario.exception.StockInsuficienteException;
import com.clinica.inventario.repository.MovimientoRepository;
import com.clinica.inventario.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final ProductoRepository productoRepository;
    private final MovimientoRepository movimientoRepository;

    @Transactional
    public Producto aumentarStock(Long productoId, StockRequestDTO request) {
        Producto producto = productoRepository.findById(productoId)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", productoId));

        producto.setCantidadDisponible(producto.getCantidadDisponible() + request.getCantidad());
        producto.setActualizadoPor(request.getUsuario());

        if (producto.getEstado() == EstadoInventario.AGOTADO
            && producto.getCantidadDisponible() > 0) {
            producto.setEstado(EstadoInventario.ACTIVO);
        }

        Producto saved = productoRepository.save(producto);
        registrarMovimiento(productoId, TipoMovimiento.ENTRADA, request.getCantidad(),
            request.getUsuario(), request.getMotivo(), request.getObservaciones());
        return saved;
    }

    @Transactional
    public Producto disminuirStock(Long productoId, StockRequestDTO request) {
        Producto producto = productoRepository.findById(productoId)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", productoId));

        if (producto.getFechaVencimiento().isBefore(LocalDate.now())) {
            throw new ProductoVencidoException(productoId, producto.getFechaVencimiento());
        }

        if (producto.getCantidadDisponible() < request.getCantidad()) {
            throw new StockInsuficienteException(productoId,
                producto.getCantidadDisponible(), request.getCantidad());
        }

        producto.setCantidadDisponible(producto.getCantidadDisponible() - request.getCantidad());
        producto.setActualizadoPor(request.getUsuario());

        if (producto.getCantidadDisponible() == 0) {
            producto.setEstado(EstadoInventario.AGOTADO);
        }

        Producto saved = productoRepository.save(producto);
        registrarMovimiento(productoId, TipoMovimiento.SALIDA, request.getCantidad(),
            request.getUsuario(), request.getMotivo(), request.getObservaciones());
        return saved;
    }

    @Transactional
    public Producto ajustarStock(Long productoId, StockRequestDTO request) {
        Producto producto = productoRepository.findById(productoId)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", productoId));

        if (request.getCantidad() < 0) {
            throw new IllegalArgumentException("La cantidad de ajuste no puede ser negativa");
        }

        producto.setCantidadDisponible(request.getCantidad());
        producto.setActualizadoPor(request.getUsuario());

        if (producto.getCantidadDisponible() == 0) {
            producto.setEstado(EstadoInventario.AGOTADO);
        } else if (producto.getEstado() == EstadoInventario.AGOTADO) {
            producto.setEstado(EstadoInventario.ACTIVO);
        }

        Producto saved = productoRepository.save(producto);
        registrarMovimiento(productoId, TipoMovimiento.AJUSTE, request.getCantidad(),
            request.getUsuario(), request.getMotivo(), request.getObservaciones());
        return saved;
    }

    private void registrarMovimiento(Long productoId, TipoMovimiento tipo,
                                      int cantidad, String usuario,
                                      String motivo, String observaciones) {
        Movimiento movimiento = Movimiento.builder()
            .productoId(productoId)
            .tipoMovimiento(tipo)
            .cantidad(cantidad)
            .usuario(usuario)
            .motivo(motivo)
            .observaciones(observaciones)
            .build();
        movimientoRepository.save(movimiento);
    }
}
