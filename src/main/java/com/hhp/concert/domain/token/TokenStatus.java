package com.hhp.concert.domain.token;

public enum TokenStatus {
    ACTIVE,  // 대기열을 마치고 유효한 상태 토큰
    PENDING, // 대기열에 있는 상태. 대기 중
    EXPIRED // 만료된 토큰 상태
}
