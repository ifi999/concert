package com.hhp.concert.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConcertException.class)
    public ResponseEntity<ExceptionResponse> handleConcertException(ConcertException ex) {
        return ResponseEntity.ok(new ExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGlobalException(Exception ex) {
        return ResponseEntity.ok(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), LocalDateTime.now()));
    }

}
