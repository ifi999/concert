package com.hhp.concert.infra.concert;

import com.hhp.concert.domain.concert.ConcertRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    public ConcertRepositoryImpl(final ConcertJpaRepository concertJpaRepository) {
        this.concertJpaRepository = concertJpaRepository;
    }

}
