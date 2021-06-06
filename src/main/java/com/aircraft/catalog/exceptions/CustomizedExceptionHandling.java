package com.aircraft.catalog.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aircraft.catalog.model.ExceptionResponse;

@ControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleExceptions(ProductNotFoundException exception, WebRequest webRequest) {
        ExceptionResponse response = ExceptionResponse.builder()
        							.dateTime(LocalDateTime.now())
        							.message(exception.getErrorMessage())
        							.build();
        
        return ResponseEntity
        		.status(exception.getErrorCode())
        		.body(response);
    }
}