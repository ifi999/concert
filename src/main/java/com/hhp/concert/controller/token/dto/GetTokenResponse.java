package com.hhp.concert.controller.token.dto;

import com.hhp.concert.domain.token.Token;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetTokenResponse {

    private long tokenId;
    private long userId;
    private String token;
    private LocalDateTime createdAt;
    private long queueNumber;

    @Builder
    public GetTokenResponse(
        final long tokenId,
        final long userId,
        final String token,
        final LocalDateTime createdAt,
        final long queueNumber
    ) {
        this.tokenId = tokenId;
        this.userId = userId;
        this.token = token;
        this.createdAt = createdAt;
        this.queueNumber = queueNumber;
    }

    public static GetTokenResponse from(final Token token) {
        return GetTokenResponse.builder()
            .tokenId(token.getTokenId())
            .userId(token.getUserId())
            .token(token.getToken())
            .createdAt(token.getCreatedAt())
            .queueNumber(token.getQueueNumber())
            .build();
    }
}
