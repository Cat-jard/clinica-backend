package com.clinica.inventario.exception;

public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(Long productoId, int disponible, int solicitado) {
        super("Stock insuficiente para el producto " + productoId
            + ". Disponible: " + disponible + ", solicitado: " + solicitado);
    }
}
