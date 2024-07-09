package com.hhp.concert.infra.user;

import com.hhp.concert.domain.user.ConcertUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ConcertUserRepositoryImpl implements ConcertUserRepository {

    private ConcertUserJpaRepository concertUserJpaRepository;

    public ConcertUserRepositoryImpl(final ConcertUserJpaRepository concertUserJpaRepository) {
        this.concertUserJpaRepository = concertUserJpaRepository;
    }

}
