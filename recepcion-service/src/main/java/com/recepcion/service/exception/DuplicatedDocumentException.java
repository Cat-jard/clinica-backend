package com.recepcion.service.exception;

public class DuplicatedDocumentException extends RuntimeException {

    public DuplicatedDocumentException(String message) {
        super(message);
    }
}
