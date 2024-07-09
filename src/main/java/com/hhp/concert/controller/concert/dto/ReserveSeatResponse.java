package com.hhp.concert.controller.concert.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReserveSeatResponse {

    private long reservationId;
    private String seatName;
    private LocalDateTime reservationTime;
    private String reservationStatus;

    public ReserveSeatResponse(final long reservationId, final String seatName, final LocalDateTime reservationTime, final String reservationStatus) {
        this.reservationId = reservationId;
        this.seatName = seatName;
        this.reservationTime = reservationTime;
        this.reservationStatus = reservationStatus;
    }

}
