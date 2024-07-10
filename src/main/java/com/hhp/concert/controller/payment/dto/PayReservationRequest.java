package com.hhp.concert.controller.payment.dto;

import lombok.Getter;

@Getter
public class
PayReservationRequest {

    private long reservationId;
    private long userId;
    private long paymentAmount;

    public PayReservationRequest(final long reservationId, final long userId, final long paymentAmount) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.paymentAmount = paymentAmount;
    }

}
