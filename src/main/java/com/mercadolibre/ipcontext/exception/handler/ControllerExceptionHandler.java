package com.mercadolibre.ipcontext.exception.handler;

import com.mercadolibre.ipcontext.exception.ClientRequestErrorException;
import com.mercadolibre.ipcontext.exception.IpAddressIsBannedException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessageCustom> handleGeneralException(RuntimeException exception) {
        return ResponseEntity
                .badRequest()
                .body(errorMessageCustom(exception.getMessage()));
    }

    @ExceptionHandler(ClientRequestErrorException.class)
    public ResponseEntity<ErrorMessageCustom> clientRequestErrorExceptionHandler(ClientRequestErrorException exception) {
        return ResponseEntity
                .badRequest()
                .body(errorMessageCustom(exception.getMessage()));
    }

    @ExceptionHandler(IpAddressIsBannedException.class)
    public ResponseEntity<ErrorMessageCustom> ipAddressIsBannedExceptionHandler(IpAddressIsBannedException exception) {
        return ResponseEntity
                .badRequest()
                .body(errorMessageCustom(exception.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessageCustom> constraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity
                .badRequest()
                .body(errorMessageCustom(exception.getMessage()));
    }

    private ErrorMessageCustom errorMessageCustom(String detail) {
        return ErrorMessageCustom.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .code(HttpStatus.BAD_REQUEST.value())
                .detail(detail)
                .build();
    }

}
