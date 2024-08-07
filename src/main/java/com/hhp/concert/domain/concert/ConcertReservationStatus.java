package com.hhp.concert.domain.concert;

public enum ConcertReservationStatus {
    PENDING, // 결제 대기. 예약 요청
    CONFIRMED, // 결제 완료. 예약 확정
    CANCELLED, // 예약 취소
    EXPIRED // 예약 만료
}
