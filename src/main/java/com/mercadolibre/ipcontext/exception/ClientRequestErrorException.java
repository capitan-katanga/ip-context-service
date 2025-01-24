package com.mercadolibre.ipcontext.exception;

public class ClientRequestErrorException extends RuntimeException {
    public ClientRequestErrorException(String msg) {
        super(msg);
    }
}
