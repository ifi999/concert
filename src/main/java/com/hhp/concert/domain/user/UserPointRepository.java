package com.hhp.concert.domain.user;

public interface UserPointRepository {

    UserPoint getUserPoint(ConcertUser user);

    UserPoint getBalance(Long id);

    UserPoint getUserPointByUserId(Long userId);

    UserPoint updateUserPoint(UserPoint userPoint);

}
