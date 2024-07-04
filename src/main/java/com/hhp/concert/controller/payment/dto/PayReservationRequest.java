package com.hhp.concert.controller.payment.dto;

import lombok.Getter;

@Getter
public class PayReservationRequest {

    private long reservationId;
    private int paymentAmount;

}
