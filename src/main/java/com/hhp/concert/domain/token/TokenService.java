package com.hhp.concert.domain.token;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(final TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token getToken(final Token token) {
        final Token tokenByUserId = tokenRepository.getTokenByUserId(token.getUserId());
        final List<Long> oldestPendingTokenList = tokenRepository.getOldestPendingToken();
        final Long oldestPendingTokenId =  oldestPendingTokenList.isEmpty() ? tokenByUserId.getTokenId() : oldestPendingTokenList.get(0);

        return Token.builder()
            .tokenId(tokenByUserId.getTokenId())
            .userId(tokenByUserId.getUserId())
            .token(tokenByUserId.getToken())
            .createdAt(tokenByUserId.getCreatedAt())
            .queueNumber(oldestPendingTokenId - tokenByUserId.getTokenId())
            .build();
    }

    public Token renewToken(final Long tokenId) {
        final Token pendingToken = tokenRepository.findPendingToken(tokenId);
        final List<Long> oldestPendingTokenList = tokenRepository.getOldestPendingToken();
        final Long oldestPendingTokenId = oldestPendingTokenList.isEmpty() ? pendingToken.getTokenId() : oldestPendingTokenList.get(0);

        pendingToken.validActive(oldestPendingTokenId);

        return Token.builder()
            .tokenId(pendingToken.getTokenId())
            .userId(pendingToken.getUserId())
            .token(pendingToken.getToken())
            .createdAt(pendingToken.getCreatedAt())
            .queueNumber(pendingToken.getQueueNumber())
            .build();
    }

}
