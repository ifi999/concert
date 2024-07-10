package com.hhp.concert.controller.payment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserPaymentResponse {

    private Long paymentId;
    private Long userId;
    private Long reservationId;
    private Long paymentPrice;
    private LocalDateTime createdAt;

    public GetUserPaymentResponse(
        final Long paymentId,
        final Long userId,
        final Long reservationId,
        final Long paymentPrice,
        final LocalDateTime createdAt
    ) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.reservationId = reservationId;
        this.paymentPrice = paymentPrice;
        this.createdAt = createdAt;
    }

}
