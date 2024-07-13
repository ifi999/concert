package com.hhp.concert.controller.token.dto;

import com.hhp.concert.domain.token.Token;
import lombok.Getter;

@Getter
public class GetTokenRequest {

    private long userId;

    private GetTokenRequest() {
    }

    public GetTokenRequest(final long userId) {
        this.userId = userId;
    }

    public Token toDomain() {
        return new Token(this.userId);
    }
}
