package com.hhp.concert.support.exception;

public enum ExceptionCode {

    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    USER_POINT_NOT_FOUND("사용자 포인트를 찾을 수 없습니다."),
    CONCERT_NOT_FOUND("콘서트를 찾을 수 없습니다."),
    CONCERT_SCHEDULE_NOT_FOUND("콘서트 스케쥴을 찾을 수 없습니다."),
    CONCERT_SEAT_NOT_FOUND("콘서트 좌석을 찾을 수 없습니다."),
    SEAT_NOT_FOUND("좌석을 찾을 수 없습니다."),
    RESERVATION_NOT_FOUND("예약을 찾을 수 없습니다."),
    AUTH_TOKEN_NOT_FOUND("인증 토큰을 찾을 수 없습니다."),
    RESERVATION_ALREADY_RESERVED("이미 예약된 좌석입니다.");

    private String message;

    ExceptionCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
