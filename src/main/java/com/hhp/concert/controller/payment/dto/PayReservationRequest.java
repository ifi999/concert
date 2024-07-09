package com.hhp.concert.controller.payment.dto;

import lombok.Getter;

@Getter
public class PayReservationRequest {

    private long reservationId;
    private int paymentAmount;

    public PayReservationRequest(final long reservationId, final int paymentAmount) {
        this.reservationId = reservationId;
        this.paymentAmount = paymentAmount;
    }

}
