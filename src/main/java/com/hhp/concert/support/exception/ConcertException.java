package com.hhp.concert.support.exception;

public class ConcertException extends RuntimeException {

    private ExceptionCode exceptionCode;
    private String message;

    public ConcertException(final ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
        this.message = exceptionCode.getMessage();
    }

}
