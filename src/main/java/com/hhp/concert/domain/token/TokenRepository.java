package com.hhp.concert.domain.token;

public interface TokenRepository {

    Token getTokenByUserId(Long userId);

    Token renewToken(Long tokenId);

}
