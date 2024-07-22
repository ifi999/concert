package com.hhp.concert.controller.user.dto;

import com.hhp.concert.domain.user.UserPoint;
import lombok.Getter;

@Getter
public class GetBalanceResponse {

    private long userId;
    private long balance;

    public GetBalanceResponse(final long userId, final long balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public static GetBalanceResponse from(final UserPoint userPoint) {
        return new GetBalanceResponse(userPoint.getUserId(), userPoint.getPoint());
    }

}
