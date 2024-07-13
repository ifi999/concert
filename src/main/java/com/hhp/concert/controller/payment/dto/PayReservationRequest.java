package com.hhp.concert.controller.payment.dto;

import com.hhp.concert.domain.payment.Payment;
import lombok.Getter;

@Getter
public class PayReservationRequest {

    private long reservationId;
    private long userId;
    private long paymentAmount;

    public PayReservationRequest(final long reservationId, final long userId, final long paymentAmount) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.paymentAmount = paymentAmount;
    }

    public Payment toDomain() {
        return new Payment(this.reservationId, this.userId, this.paymentAmount);
    }

}
