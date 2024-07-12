package com.hhp.concert.infra.concert;

import com.hhp.concert.domain.concert.Concert;
import com.hhp.concert.domain.concert.ConcertSchedule;
import com.hhp.concert.domain.concert.ConcertSeat;
import com.hhp.concert.domain.concert.ConcertSeatRepository;
import com.hhp.concert.infra.concert.entity.ConcertEntity;
import com.hhp.concert.infra.concert.entity.ConcertScheduleEntity;
import com.hhp.concert.infra.concert.entity.ConcertSeatEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConcertSeatRepositoryImpl implements ConcertSeatRepository {

    private final ConcertJpaRepository concertJpaRepository;
    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;
    private final ConcertSeatJpaRepository concertSeatJpaRepository;

    public ConcertSeatRepositoryImpl(
        final ConcertJpaRepository concertJpaRepository,
        final ConcertScheduleJpaRepository concertScheduleJpaRepository,
        final ConcertSeatJpaRepository concertSeatJpaRepository
    ) {
        this.concertJpaRepository = concertJpaRepository;
        this.concertScheduleJpaRepository = concertScheduleJpaRepository;
        this.concertSeatJpaRepository = concertSeatJpaRepository;
    }

    @Override
    public List<ConcertSeat> getConcertScheduleSeats(final Concert concert, final ConcertSchedule schedule) {
        final ConcertEntity concertEntity = concertJpaRepository.findById(concert.getId())
            .orElseThrow(() -> new EntityNotFoundException("Concert not found. ID: " + concert.getId()));

        final ConcertScheduleEntity concertScheduleEntity = concertScheduleJpaRepository.findById(schedule.getConcertScheduleId())
            .orElseThrow(() -> new EntityNotFoundException("Concert schedule not found. ID: " + schedule.getConcertScheduleId()));

        final List<ConcertSeatEntity> concertSeatEntities = concertSeatJpaRepository.findByConcertAndConcertSchedule(concertEntity, concertScheduleEntity);

        return concertSeatEntities.stream()
            .map(o -> ConcertSeat.builder()
                .concertSeatId(o.getId())
                .concertId(o.getConcert().getId())
                .scheduleId(o.getConcertSchedule().getId())
                .seatId(o.getSeat().getId())
                .zoneName(o.getSeat().getSeatZone().getZoneName())
                .seatType(o.getSeat().getSeatType().getTypeName())
                .price(o.getSeat().getSeatType().getPrice())
                .build())
            .toList();
    }

}
