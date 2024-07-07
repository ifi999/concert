package com.hhp.concert.controller.user.dto;

import lombok.Getter;

@Getter
public class GetTokenResponse {

    private long userId;
    private String token;

    public GetTokenResponse(final long userId, final String token) {
        this.userId = userId;
        this.token = token;
    }

}
