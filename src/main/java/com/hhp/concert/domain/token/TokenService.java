package com.hhp.concert.domain.token;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(final TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token getToken(final Token token) {
        return tokenRepository.getTokenByUserId(token.getUserId());
    }

    public Token renewToken(final Long tokenId) {
        return tokenRepository.renewToken(tokenId);
    }

}
