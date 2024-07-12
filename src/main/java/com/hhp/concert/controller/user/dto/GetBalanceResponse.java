package com.hhp.concert.controller.user.dto;

import lombok.Getter;

@Getter
public class GetBalanceResponse {

    private long userId;
    private long balance;

    public GetBalanceResponse(final long userId, final long balance) {
        this.userId = userId;
        this.balance = balance;
    }

}
