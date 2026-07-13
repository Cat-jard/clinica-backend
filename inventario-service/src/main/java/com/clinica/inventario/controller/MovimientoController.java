package com.clinica.inventario.controller;

import com.clinica.inventario.dto.MovimientoDTO;
import com.clinica.inventario.enums.TipoMovimiento;
import com.clinica.inventario.mapper.ProductoMapper;
import com.clinica.inventario.service.MovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
@Tag(name = "Movimientos", description = "API para historial de movimientos de inventario")
public class MovimientoController {

    private final MovimientoService movimientoService;
    private final ProductoMapper productoMapper;

    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Listar movimientos por producto")
    public ResponseEntity<List<MovimientoDTO>> listarPorProducto(@PathVariable Long productoId) {
        List<MovimientoDTO> movimientos = movimientoService.listarPorProducto(productoId)
            .stream().map(productoMapper::toMovimientoDTO).toList();
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/producto/{productoId}/tipo/{tipo}")
    @Operation(summary = "Listar movimientos por producto y tipo")
    public ResponseEntity<List<MovimientoDTO>> listarPorProductoYTipo(
            @PathVariable Long productoId, @PathVariable TipoMovimiento tipo) {
        List<MovimientoDTO> movimientos = movimientoService.listarPorProductoYTipo(productoId, tipo)
            .stream().map(productoMapper::toMovimientoDTO).toList();
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener movimiento por ID")
    public ResponseEntity<MovimientoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoMapper.toMovimientoDTO(movimientoService.obtenerPorId(id)));
    }
}
