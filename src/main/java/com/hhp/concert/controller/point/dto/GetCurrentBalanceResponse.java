package com.hhp.concert.controller.point.dto;

import lombok.Getter;

@Getter
public class GetCurrentBalanceResponse {

    private long userId;
    private long currentBalance;

    public GetCurrentBalanceResponse(final long userId, final long currentBalance) {
        this.userId = userId;
        this.currentBalance = currentBalance;
    }

}
