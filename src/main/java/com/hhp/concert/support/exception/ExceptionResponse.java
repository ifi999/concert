package com.hhp.concert.support.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponse {

    private HttpStatus httpStatus;
    private String message;
    private LocalDateTime timestamp;

    public ExceptionResponse(final HttpStatus httpStatus, final String message, final LocalDateTime timestamp) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.timestamp = timestamp;
    }

}
