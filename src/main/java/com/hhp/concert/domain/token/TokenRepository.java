package com.hhp.concert.domain.token;

import java.util.List;

public interface TokenRepository {

    Token getTokenByUserId(Long userId);

    List<Long> getOldestPendingToken();

    Token getPendingToken(Long tokenId);

    boolean auth(String token);

    Token getPendingToken(String requestToken);

    void updateToken(Token token);

}
