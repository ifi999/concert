package com.hhp.concert.controller.token.dto;

import lombok.Getter;

@Getter
public class GetTokenRequest {

    private long userId;

    private GetTokenRequest() {
    }

    public GetTokenRequest(final long userId) {
        this.userId = userId;
    }

}
