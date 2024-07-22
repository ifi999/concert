package com.hhp.concert.controller.payment.dto;

import com.hhp.concert.domain.payment.Payment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetUserPaymentResponse {

    private long paymentId;
    private long userId;
    private long reservationId;
    private long paymentPrice;
    private LocalDateTime paymentTime;

    @Builder
    public GetUserPaymentResponse(
        final long paymentId,
        final long userId,
        final long reservationId,
        final long paymentPrice,
        final LocalDateTime paymentTime
    ) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.reservationId = reservationId;
        this.paymentPrice = paymentPrice;
        this.paymentTime = paymentTime;
    }

    public static List<GetUserPaymentResponse> from(final List<Payment> payments) {
        return payments.stream()
            .map(o -> GetUserPaymentResponse.builder()
                .paymentId(o.getPaymentId())
                .userId(o.getUserId())
                .reservationId(o.getReservationId())
                .paymentPrice(o.getPaymentAmount())
                .paymentTime(o.getCreatedAt())
                .build())
            .toList();
    }

}
