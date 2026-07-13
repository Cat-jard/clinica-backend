package com.clinica.inventario.service;

import com.clinica.inventario.dto.ProductoRequestDTO;
import com.clinica.inventario.entity.Producto;
import com.clinica.inventario.enums.EstadoInventario;
import com.clinica.inventario.exception.ResourceNotFoundException;
import com.clinica.inventario.mapper.ProductoMapper;
import com.clinica.inventario.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Transactional(readOnly = true)
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
    }

    @Transactional(readOnly = true)
    public Producto obtenerPorCodigo(String codigoProducto) {
        return productoRepository.findByCodigoProducto(codigoProducto)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", codigoProducto));
    }

    @Transactional
    public Producto crear(ProductoRequestDTO requestDTO) {
        if (productoRepository.existsByCodigoProducto(requestDTO.getCodigoProducto())) {
            throw new IllegalArgumentException(
                "Ya existe un producto con el código: " + requestDTO.getCodigoProducto());
        }
        Producto producto = productoMapper.toEntity(requestDTO);
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizar(Long id, ProductoRequestDTO requestDTO) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));

        if (!producto.getCodigoProducto().equals(requestDTO.getCodigoProducto())
            && productoRepository.existsByCodigoProducto(requestDTO.getCodigoProducto())) {
            throw new IllegalArgumentException(
                "Ya existe un producto con el código: " + requestDTO.getCodigoProducto());
        }

        productoMapper.updateEntity(requestDTO, producto);
        producto.setActualizadoPor(requestDTO.getCreadoPor());
        return productoRepository.save(producto);
    }

    @Transactional
    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        productoRepository.delete(producto);
    }

    @Transactional(readOnly = true)
    public List<Producto> consultarStockBajo() {
        return productoRepository.findProductosConStockBajo();
    }

    @Transactional(readOnly = true)
    public List<Producto> consultarProximosAVencer() {
        LocalDate hoy = LocalDate.now();
        LocalDate fin = hoy.plusDays(30);
        return productoRepository.findProductosProximosAVencer(hoy, fin);
    }

    @Transactional(readOnly = true)
    public List<Producto> consultarVencidos() {
        return productoRepository.findProductosVencidos(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public int consultarStockDisponible(Long id) {
        return productoRepository.findCantidadDisponibleById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
    }

    @Transactional(readOnly = true)
    public List<Producto> consultarPorEstado(EstadoInventario estado) {
        return productoRepository.findByEstado(estado);
    }
}
