package com.hhp.concert.controller.point.dto;

import com.hhp.concert.domain.point.UserPoint;
import lombok.Getter;

@Getter
public class ChargePointResponse {

    private long userId;
    private long currentBalance;

    public ChargePointResponse(final long userId, final long currentBalance) {
        this.userId = userId;
        this.currentBalance = currentBalance;
    }

    public static ChargePointResponse from(final UserPoint chargedUserPoint) {
        return new ChargePointResponse(chargedUserPoint.getUserId(), chargedUserPoint.getPoint());
    }

}
