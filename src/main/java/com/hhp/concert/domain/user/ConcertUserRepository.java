package com.hhp.concert.domain.user;

public interface ConcertUserRepository {

    ConcertUser enroll(ConcertUser user);

    ConcertUser getUserById(Long userId);

}
