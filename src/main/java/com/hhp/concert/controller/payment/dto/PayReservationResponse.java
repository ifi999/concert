package com.hhp.concert.controller.payment.dto;

import com.hhp.concert.domain.payment.Payment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PayReservationResponse {

    private long paymentId;
    private long reservationId;
    private long userId;
    private long paymentAmount;
    private LocalDateTime paymentTime;

    @Builder
    public PayReservationResponse(
        final long paymentId,
        final long reservationId,
        final long userId,
        final long paymentAmount,
        final LocalDateTime paymentTime
    ) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.userId = userId;
        this.paymentAmount = paymentAmount;
        this.paymentTime = paymentTime;
    }

    public static PayReservationResponse from(final Payment payment) {
        return PayReservationResponse.builder()
            .paymentId(payment.getPaymentId())
            .reservationId(payment.getReservationId())
            .userId(payment.getUserId())
            .paymentAmount(payment.getPaymentAmount())
            .paymentTime(payment.getCreatedAt())
            .build();
    }
}
