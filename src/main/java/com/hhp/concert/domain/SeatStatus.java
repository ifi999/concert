package com.hhp.concert.domain;

public enum SeatStatus {
    AVAILABLE, // 예약 가능
    RESERVED, // 예약 중
    SOLD, // 사용 중. 결제 완료
    BLOCKED // 시스템 상 막은 좌석. ex) 수리 중
}
