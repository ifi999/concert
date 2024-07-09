package com.hhp.concert.controller.concert.dto;

import lombok.Getter;

@Getter
public class ReserveSeatRequest {

    private long seatId;
    private long userId;

    public ReserveSeatRequest(final long seatId, final long userId) {
        this.seatId = seatId;
        this.userId = userId;
    }

}
