package com.clinica.inventario.exception;

import java.time.LocalDate;

public class ProductoVencidoException extends RuntimeException {

    public ProductoVencidoException(Long productoId, LocalDate fechaVencimiento) {
        super("El producto " + productoId + " está vencido desde: " + fechaVencimiento);
    }
}
