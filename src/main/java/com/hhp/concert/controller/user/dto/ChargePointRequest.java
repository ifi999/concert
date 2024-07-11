package com.hhp.concert.controller.user.dto;

import com.hhp.concert.domain.user.UserPoint;
import lombok.Getter;

@Getter
public class ChargePointRequest {


    private long amount;

    public ChargePointRequest() {
    }

    public ChargePointRequest(final long amount) {
        this.amount = amount;
    }

    public UserPoint toDomain() {
        return new UserPoint(this.amount);
    }

}
