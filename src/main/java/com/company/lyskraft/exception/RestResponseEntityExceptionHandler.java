package com.company.lyskraft.exception;

import lombok.NonNull;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { Exception.class, RuntimeException.class })
    protected ResponseEntity<Object> handleGlobalException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, new ErrorMessage(exception.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException exception,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        return handleExceptionInternal(exception, new ErrorMessage(exception.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}