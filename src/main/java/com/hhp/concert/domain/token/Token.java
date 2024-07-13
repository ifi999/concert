package com.hhp.concert.domain.token;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Token {

    private Long tokenId;
    private Long userId;
    private String token;
    private TokenStatus tokenStatus;
    private Long queueNumber;
    private LocalDateTime createdAt;
    private LocalDateTime entryTime;

    public Token(final Long userId) {
        this.userId = userId;
    }

    @Builder
    public Token(
        final Long tokenId,
        final Long userId,
        final String token,
        final TokenStatus tokenStatus,
        final Long queueNumber,
        final LocalDateTime createdAt
    ) {
        this.tokenId = tokenId;
        this.userId = userId;
        this.token = token;
        this.tokenStatus = tokenStatus;
        this.queueNumber = queueNumber;
        this.createdAt = createdAt;
    }

}
