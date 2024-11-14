package com.hhp.concert.event.payment;

import com.hhp.concert.domain.payment.Payment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentSuccessEvent {

    private Long paymentId;
    private Long reservationId;
    private Long userId;
    private Long paymentAmount;
    private LocalDateTime createdAt;

    public PaymentSuccessEvent(final Payment payment) {
        this.paymentId = payment.getPaymentId();
        this.reservationId = payment.getReservationId();
        this.userId = payment.getUserId();
        this.paymentAmount = payment.getPaymentAmount();
        this.createdAt = payment.getCreatedAt();
    }

}
