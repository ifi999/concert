package com.hhp.concert.controller.token.dto;

import com.hhp.concert.domain.token.TokenStatus;
import lombok.Getter;

@Getter
public class GetTokenResponse {

    private long userId;
    private long tokenId;
    private String token;
    private TokenStatus tokenStatus;
    private int queueNumber;

    public GetTokenResponse(
        final long userId,
        final long tokenId,
        final String token,
        final TokenStatus tokenStatus,
        final int queueNumber
    ) {
        this.userId = userId;
        this.tokenId = tokenId;
        this.token = token;
        this.tokenStatus = tokenStatus;
        this.queueNumber = queueNumber;
    }

}
