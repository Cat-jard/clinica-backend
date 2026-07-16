package com.clinica.inventario.mapper;

import com.clinica.inventario.dto.MovimientoDTO;
import com.clinica.inventario.dto.ProductoDTO;
import com.clinica.inventario.dto.ProductoRequestDTO;
import com.clinica.inventario.entity.Movimiento;
import com.clinica.inventario.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoDTO toDTO(Producto producto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "actualizadoPor", ignore = true)
    Producto toEntity(ProductoRequestDTO requestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigoProducto", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "creadoPor", ignore = true)
    void updateEntity(ProductoRequestDTO requestDTO, @MappingTarget Producto producto);

    MovimientoDTO toMovimientoDTO(Movimiento movimiento);
}
