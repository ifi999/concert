package com.hhp.concert.controller.token.dto;

import com.hhp.concert.domain.token.Token;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RenewTokenResponse {

    private long userId;
    private long tokenId;
    private String token;
    private long queueNumber;

    @Builder
    public RenewTokenResponse(
        final long userId,
        final long tokenId,
        final String token,
        final long queueNumber
    ) {
        this.userId = userId;
        this.tokenId = tokenId;
        this.token = token;
        this.queueNumber = queueNumber;
    }

    public static RenewTokenResponse from(final Token token) {
        return RenewTokenResponse.builder()
                .userId(token.getUserId())
                .tokenId(token.getTokenId())
                .token(token.getToken())
                .queueNumber(token.getQueueNumber())
                .build();
    }

}
