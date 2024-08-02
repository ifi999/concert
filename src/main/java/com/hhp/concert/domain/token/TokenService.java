package com.hhp.concert.domain.token;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(final TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token getToken(final Token token) {
        final Token tokenByUserId = tokenRepository.getTokenByUserId(token.getUserId());
        final Long tokenWaitingNumber = tokenRepository.getTokenPendingNumber(token.getUserId());

        return Token.builder()
            .tokenId(tokenByUserId.getTokenId())
            .userId(tokenByUserId.getUserId())
            .token(tokenByUserId.getToken())
            .createdAt(tokenByUserId.getCreatedAt())
            .queueNumber(tokenWaitingNumber)
            .build();
    }

    public Token renewToken(final Long tokenId) {
        final Token pendingToken = tokenRepository.getPendingToken(tokenId);
        final Long tokenWaitingNumber = tokenRepository.getTokenPendingNumber(pendingToken.getUserId());

        return Token.builder()
            .tokenId(pendingToken.getTokenId())
            .userId(pendingToken.getUserId())
            .token(pendingToken.getToken())
            .createdAt(pendingToken.getCreatedAt())
            .queueNumber(tokenWaitingNumber)
            .build();
    }

    public boolean auth(final String requestToken) {
        return tokenRepository.auth(requestToken);
    }

    public void updateTokenActivityTime(final String requestToken) {
        final Token token = tokenRepository.getPendingToken(requestToken);
        token.updateActiveTime();

        tokenRepository.updateToken(token);
    }

    public void activeTokens(final Integer activeRange) {
        tokenRepository.activeTokens(activeRange);
    }

}
