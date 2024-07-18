package com.hhp.concert.domain.token;

import java.util.List;

public interface TokenRepository {

    Token getTokenByUserId(Long userId);

    List<Long> getOldestPendingToken();

    Token findPendingToken(Long tokenId);

}
