package com.mercadolibre.ipcontext.exception;

public class ClientApiErrorException extends RuntimeException {
    public ClientApiErrorException(String msg) {
        super(msg);
    }
}
