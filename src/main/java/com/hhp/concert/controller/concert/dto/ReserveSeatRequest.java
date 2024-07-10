package com.hhp.concert.controller.concert.dto;

import lombok.Getter;

@Getter
public class ReserveSeatRequest {

    private long concertId;
    private long scheduleId;
    private long seatId;
    private long userId;

    public ReserveSeatRequest(final long concertId, final long scheduleId, final long seatId, final long userId) {
        this.concertId = concertId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.userId = userId;
    }

}
