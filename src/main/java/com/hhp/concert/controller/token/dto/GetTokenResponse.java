package com.hhp.concert.controller.token.dto;

import com.hhp.concert.domain.token.Token;
import com.hhp.concert.domain.token.TokenStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetTokenResponse {

    private long tokenId;
    private long userId;
    private String token;
    private TokenStatus tokenStatus;
    private LocalDateTime createdAt;
    private long queueNumber;

    @Builder
    public GetTokenResponse(
        final long tokenId,
        final long userId,
        final String token,
        final TokenStatus tokenStatus,
        final LocalDateTime createdAt,
        final long queueNumber
    ) {
        this.tokenId = tokenId;
        this.userId = userId;
        this.token = token;
        this.tokenStatus = tokenStatus;
        this.createdAt = createdAt;
        this.queueNumber = queueNumber;
    }

    public static GetTokenResponse from(final Token token) {
        return GetTokenResponse.builder()
            .tokenId(token.getTokenId())
            .userId(token.getUserId())
            .token(token.getToken())
            .tokenStatus(token.getTokenStatus())
            .createdAt(token.getCreatedAt())
            .queueNumber(token.getQueueNumber())
            .build();
    }
}
