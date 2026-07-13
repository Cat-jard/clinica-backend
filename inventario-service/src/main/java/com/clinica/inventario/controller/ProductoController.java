package com.clinica.inventario.controller;

import com.clinica.inventario.dto.ProductoDTO;
import com.clinica.inventario.dto.ProductoRequestDTO;
import com.clinica.inventario.dto.StockRequestDTO;
import com.clinica.inventario.entity.Producto;
import com.clinica.inventario.enums.EstadoInventario;
import com.clinica.inventario.mapper.ProductoMapper;
import com.clinica.inventario.service.InventarioService;
import com.clinica.inventario.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "API para gestión de productos del inventario")
public class ProductoController {

    private final ProductoService productoService;
    private final InventarioService inventarioService;
    private final ProductoMapper productoMapper;

    @GetMapping
    @Operation(summary = "Listar todos los productos")
    public ResponseEntity<List<ProductoDTO>> listarTodos() {
        List<ProductoDTO> productos = productoService.listarTodos()
            .stream().map(productoMapper::toDTO).toList();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        ProductoDTO dto = productoMapper.toDTO(productoService.obtenerPorId(id));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/codigo/{codigoProducto}")
    @Operation(summary = "Obtener producto por código")
    public ResponseEntity<ProductoDTO> obtenerPorCodigo(@PathVariable String codigoProducto) {
        ProductoDTO dto = productoMapper.toDTO(productoService.obtenerPorCodigo(codigoProducto));
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto")
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoRequestDTO requestDTO) {
        Producto producto = productoService.crear(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoMapper.toDTO(producto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody ProductoRequestDTO requestDTO) {
        Producto producto = productoService.actualizar(id, requestDTO);
        return ResponseEntity.ok(productoMapper.toDTO(producto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/stock/entrada")
    @Operation(summary = "Registrar entrada de stock")
    public ResponseEntity<ProductoDTO> aumentarStock(@PathVariable Long id,
                                                      @Valid @RequestBody StockRequestDTO request) {
        Producto producto = inventarioService.aumentarStock(id, request);
        return ResponseEntity.ok(productoMapper.toDTO(producto));
    }

    @PostMapping("/{id}/stock/salida")
    @Operation(summary = "Registrar salida de stock (dispensación)")
    public ResponseEntity<ProductoDTO> disminuirStock(@PathVariable Long id,
                                                       @Valid @RequestBody StockRequestDTO request) {
        Producto producto = inventarioService.disminuirStock(id, request);
        return ResponseEntity.ok(productoMapper.toDTO(producto));
    }

    @PostMapping("/{id}/stock/ajuste")
    @Operation(summary = "Ajustar stock a un valor específico")
    public ResponseEntity<ProductoDTO> ajustarStock(@PathVariable Long id,
                                                     @Valid @RequestBody StockRequestDTO request) {
        Producto producto = inventarioService.ajustarStock(id, request);
        return ResponseEntity.ok(productoMapper.toDTO(producto));
    }

    @GetMapping("/alertas/stock-bajo")
    @Operation(summary = "Consultar productos con stock por debajo del mínimo")
    public ResponseEntity<List<ProductoDTO>> stockBajo() {
        List<ProductoDTO> productos = productoService.consultarStockBajo()
            .stream().map(productoMapper::toDTO).toList();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/alertas/proximos-vencer")
    @Operation(summary = "Consultar productos próximos a vencer (30 días)")
    public ResponseEntity<List<ProductoDTO>> proximosAVencer() {
        List<ProductoDTO> productos = productoService.consultarProximosAVencer()
            .stream().map(productoMapper::toDTO).toList();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/alertas/vencidos")
    @Operation(summary = "Consultar productos vencidos")
    public ResponseEntity<List<ProductoDTO>> vencidos() {
        List<ProductoDTO> productos = productoService.consultarVencidos()
            .stream().map(productoMapper::toDTO).toList();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}/stock")
    @Operation(summary = "Consultar stock disponible de un producto")
    public ResponseEntity<Integer> stockDisponible(@PathVariable Long id) {
        int stock = productoService.consultarStockDisponible(id);
        return ResponseEntity.ok(stock);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Consultar productos por estado")
    public ResponseEntity<List<ProductoDTO>> porEstado(@PathVariable EstadoInventario estado) {
        List<ProductoDTO> productos = productoService.consultarPorEstado(estado)
            .stream().map(productoMapper::toDTO).toList();
        return ResponseEntity.ok(productos);
    }
}
