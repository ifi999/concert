package com.hhp.concert.infra.concert;

import com.hhp.concert.domain.concert.Concert;
import com.hhp.concert.domain.concert.ConcertRepository;
import com.hhp.concert.infra.concert.entity.ConcertEntity;
import jakarta.persistence.EntityNotFoundException;
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

    @Override
    public Concert getConcertById(final long concertId) {
        final ConcertEntity concertEntity = concertJpaRepository.findById(concertId)
            .orElseThrow(() -> new EntityNotFoundException("Concert not found. ID: " + concertId));

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
