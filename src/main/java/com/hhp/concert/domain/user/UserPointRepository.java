package com.hhp.concert.domain.user;

public interface UserPointRepository {

    UserPoint charge(ConcertUser user, Long point);

    UserPoint getBalance(Long id);

    UserPoint getUserPointByUserId(Long userId);

    UserPoint updateUserPoint(UserPoint userPoint);

}
