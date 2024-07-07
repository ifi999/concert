package com.hhp.concert.controller.payment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PayReservationResponse {

    private long paymentId;
    private long reservationId;
    private int paymentAmount;
    private LocalDateTime paymentTime;

    public PayReservationResponse(final long paymentId, final long reservationId, final int paymentAmount, final LocalDateTime paymentTime) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.paymentAmount = paymentAmount;
        this.paymentTime = paymentTime;
    }

}
