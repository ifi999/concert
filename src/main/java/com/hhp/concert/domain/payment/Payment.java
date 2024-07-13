package com.hhp.concert.domain.payment;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Payment {

    private Long paymentId;
    private Long reservationId;
    private Long userId;
    private Long paymentAmount;
    private LocalDateTime createdAt;

    public Payment(final Long reservationId, final Long userId, final Long paymentAmount) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.paymentAmount = paymentAmount;
    }

    @Builder
    public Payment(
        final Long paymentId,
        final Long reservationId,
        final Long userId,
        final Long paymentAmount,
        final LocalDateTime createdAt
    ) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.userId = userId;
        this.paymentAmount = paymentAmount;
        this.createdAt = createdAt;
    }

}
