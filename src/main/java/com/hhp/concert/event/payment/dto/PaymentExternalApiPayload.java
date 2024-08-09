package com.hhp.concert.event.payment.dto;

import com.hhp.concert.event.payment.PaymentSuccessEvent;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class PaymentExternalApiPayload {

    private Long paymentId;
    private Long reservationId;
    private Long userId;
    private Long paymentAmount;
    private LocalDateTime createdAt;

    public PaymentExternalApiPayload(final PaymentSuccessEvent event) {
        this.paymentId = event.getPaymentId();
        this.reservationId = event.getReservationId();
        this.userId = event.getUserId();
        this.paymentAmount = event.getPaymentAmount();
        this.createdAt = event.getCreatedAt();
    }

}
