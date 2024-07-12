package com.hhp.concert.infra.concert;

import com.hhp.concert.domain.concert.ConcertRepository;
import com.hhp.concert.infra.concert.entity.ConcertEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    public ConcertRepositoryImpl(final ConcertJpaRepository concertJpaRepository) {
        this.concertJpaRepository = concertJpaRepository;
    }

    @Override
    public List<ConcertEntity> getConcerts(final LocalDate currentDate) {
        return concertJpaRepository.findActiveConcerts(currentDate);
    }
}
