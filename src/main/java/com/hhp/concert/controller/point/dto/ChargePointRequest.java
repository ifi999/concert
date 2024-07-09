package com.hhp.concert.controller.point.dto;

import lombok.Getter;

@Getter
public class ChargePointRequest {

    private long userId;
    private long amount;

    public ChargePointRequest(final long userId, final long amount) {
        this.userId = userId;
        this.amount = amount;
    }

}
