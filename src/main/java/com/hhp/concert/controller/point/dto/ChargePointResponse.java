package com.hhp.concert.controller.point.dto;

import lombok.Getter;

@Getter
public class ChargePointResponse {

    private long userId;
    private long currentBalance;

    public ChargePointResponse(final long userId, final long currentBalance) {
        this.userId = userId;
        this.currentBalance = currentBalance;
    }

}
