package com.mercadolibre.ipcontext.exception;

public class IpAddressIsBannedException extends RuntimeException {
    public IpAddressIsBannedException(String message) {
        super(message);
    }
}
