package com.hhp.concert.infra.concert;

import com.hhp.concert.domain.concert.Concert;
import com.hhp.concert.domain.concert.ConcertRepository;
import com.hhp.concert.infra.concert.entity.ConcertEntity;
import com.hhp.concert.support.exception.ConcertException;
import com.hhp.concert.support.exception.ExceptionCode;
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
    public List<Concert> getConcerts(final LocalDate currentDate) {
        final List<ConcertEntity> activeConcerts = concertJpaRepository.findActiveConcerts(currentDate);

        return activeConcerts.stream()
            .map(o -> Concert.builder()
                .id(o.getId())
                .concertName(o.getConcertName())
                .artist(o.getArtist())
                .venue(o.getVenue())
                .startDate(o.getStartDate())
                .endDate(o.getEndDate())
                .build())
            .toList();
    }

    @Override
    public Concert getConcertById(final long concertId) {
        final ConcertEntity concertEntity = concertJpaRepository.findById(concertId)
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_NOT_FOUND));

        return Concert.builder()
            .id(concertEntity.getId())
            .concertName(concertEntity.getConcertName())
            .artist(concertEntity.getArtist())
            .venue(concertEntity.getVenue())
            .startDate(concertEntity.getStartDate())
            .endDate(concertEntity.getEndDate())
            .build();
    }

}
