package com.hhp.concert.domain.token;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Token {

    private Long tokenId;
    private Long userId;
    private String token;
    private Long queueNumber;
    private LocalDateTime createdAt;
    private LocalDateTime entryTime;
    private LocalDateTime lastActiveTime;

    public Token(final Long userId) {
        this.userId = userId;
    }

    @Builder
    public Token(
        final Long tokenId,
        final Long userId,
        final String token,
        final Long queueNumber,
        final LocalDateTime createdAt
    ) {
        this.tokenId = tokenId;
        this.userId = userId;
        this.token = token;
        this.queueNumber = queueNumber;
        this.createdAt = createdAt;
    }

    public boolean isTokenExpired() {
        return entryTime != null && LocalDateTime.now().isAfter(lastActiveTime.plusMinutes(5));
    }

    public void validActive(final Long oldestPendingTokenId) {
        this.queueNumber = oldestPendingTokenId - this.tokenId;

        if(this.queueNumber.equals(0L)) {
            this.entryTime = LocalDateTime.now();
            this.lastActiveTime = LocalDateTime.now();
            TokenQueue.add(this);
        };
    }

    public void updateActiveTime() {
        this.lastActiveTime = LocalDateTime.now();
    }

}
