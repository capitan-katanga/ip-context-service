package com.mercadolibre.ipcontext.exception.handler;

import com.mercadolibre.ipcontext.exception.ClientRequestErrorException;
import com.mercadolibre.ipcontext.exception.IpAddressIsBannedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.SocketTimeoutException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ClientRequestErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageCustom clientRequestErrorExceptionHandler(ClientRequestErrorException exception) {
        return ErrorMessageCustom.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .code(HttpStatus.BAD_REQUEST.value())
                .detail(exception.getMessage()).build();
    }

    @ExceptionHandler(SocketTimeoutException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorMessageCustom socketTimeoutExceptionGeographyClient(SocketTimeoutException exception) {
        return ErrorMessageCustom.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .detail("Geography api client not responding, please try again").build();
    }

    @ExceptionHandler(IpAddressIsBannedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageCustom ipAddressIsBannedExceptionHandler(IpAddressIsBannedException exception){
        return ErrorMessageCustom.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .code(HttpStatus.BAD_REQUEST.value())
                .detail(exception.getMessage()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageCustom methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        return ErrorMessageCustom.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .code(HttpStatus.BAD_REQUEST.value())
                .detail(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage()).build();
    }
}
