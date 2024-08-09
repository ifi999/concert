package com.hhp.concert.event.payment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentFailureEvent {

    private Long paymentId;
    private Long reservationId;
    private Long userId;
    private Long paymentAmount;
    private LocalDateTime createdAt;

    public PaymentFailureEvent(final PaymentSuccessEvent event) {
        this.paymentId = event.getPaymentId();
        this.reservationId = event.getReservationId();
        this.userId = event.getUserId();
        this.paymentAmount = event.getPaymentAmount();
        this.createdAt = event.getCreatedAt();
    }

}
