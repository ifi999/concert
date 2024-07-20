package com.hhp.concert.infra.concert;

import com.hhp.concert.domain.concert.Concert;
import com.hhp.concert.domain.concert.ConcertSchedule;
import com.hhp.concert.domain.concert.ConcertScheduleRepository;
import com.hhp.concert.infra.concert.entity.ConcertEntity;
import com.hhp.concert.infra.concert.entity.ConcertScheduleEntity;
import com.hhp.concert.support.exception.ConcertException;
import com.hhp.concert.support.exception.ExceptionCode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConcertScheduleRepositoryImpl implements ConcertScheduleRepository {

    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;

    public ConcertScheduleRepositoryImpl(final ConcertScheduleJpaRepository concertScheduleJpaRepository) {
        this.concertScheduleJpaRepository = concertScheduleJpaRepository;
    }

    @Override
    public List<ConcertSchedule> getConcertSchedulesByConcertId(final Concert concert) {
        final ConcertEntity concertEntity = ConcertEntity.builder()
            .id(concert.getId())
            .concertName(concert.getConcertName())
            .artist(concert.getArtist())
            .venue(concert.getVenue())
            .startDate(concert.getStartDate())
            .endDate(concert.getEndDate())
            .build();

        final List<ConcertScheduleEntity> concertScheduleEntityList = concertScheduleJpaRepository.findByConcert(concertEntity);

        return concertScheduleEntityList.stream()
            .map(o -> ConcertSchedule.builder()
                .concertScheduleId(o.getId())
                .concertId(o.getConcert().getId())
                .concertDate(o.getConcertDate())
                .concertTime(o.getConcertTime())
                .build())
            .toList();
    }

    @Override
    public ConcertSchedule getConcertScheduleById(final long scheduleId) {
        final ConcertScheduleEntity concertScheduleEntity = concertScheduleJpaRepository.findById(scheduleId)
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_SCHEDULE_NOT_FOUND));

        return ConcertSchedule.builder()
            .concertScheduleId(concertScheduleEntity.getId())
            .concertId(concertScheduleEntity.getConcert().getId())
            .concertDate(concertScheduleEntity.getConcertDate())
            .concertTime(concertScheduleEntity.getConcertTime())
            .build();
    }

}
