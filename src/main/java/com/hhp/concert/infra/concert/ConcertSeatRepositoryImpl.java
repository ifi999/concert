package com.hhp.concert.infra.concert;

import com.hhp.concert.domain.SeatStatus;
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

        final List<ConcertSeatEntity> concertSeatEntities = concertSeatJpaRepository.findAvailableConcertSeats(concertEntity, concertScheduleEntity);

        return concertSeatEntities.stream()
            .map(o -> ConcertSeat.builder()
                .concertSeatId(o.getId())
                .concertId(o.getConcert().getId())
                .scheduleId(o.getConcertSchedule().getId())
                .seatId(o.getSeat().getId())
                .zoneName(o.getSeat().getSeatZone().getZoneName())
                .seatType(o.getSeat().getSeatType().getTypeName())
                .seatName(o.getSeat().getSeatName())
                .price(o.getSeat().getSeatType().getPrice())
                .isAvailable(o.getSeatStatus().equals(SeatStatus.AVAILABLE))
                .build())
            .toList();
    }

    @Override
    public ConcertSeat getSeatInfo(final Concert concert, final ConcertSchedule schedule, final long seatId) {
        final ConcertEntity concertEntity = concertJpaRepository.findById(concert.getId())
            .orElseThrow(() -> new EntityNotFoundException("Concert not found. ID: " + concert.getId()));

        final ConcertScheduleEntity concertScheduleEntity = concertScheduleJpaRepository.findById(schedule.getConcertScheduleId())
            .orElseThrow(() -> new EntityNotFoundException("Concert schedule not found. ID: " + schedule.getConcertScheduleId()));

        final ConcertSeatEntity concertSeatEntity = concertSeatJpaRepository.findConcertSeatInfo(concertEntity, concertScheduleEntity, seatId)
            .orElseThrow(() -> new EntityNotFoundException("Concert seat not found. ID: " + seatId));

        return ConcertSeat.builder()
            .concertSeatId(concertSeatEntity.getId())
            .concertId(concertSeatEntity.getConcert().getId())
            .scheduleId(concertSeatEntity.getConcertSchedule().getId())
            .seatId(concertSeatEntity.getSeat().getId())
            .zoneName(concertSeatEntity.getSeat().getSeatZone().getZoneName())
            .seatType(concertSeatEntity.getSeat().getSeatType().getTypeName())
            .seatName(concertSeatEntity.getSeat().getSeatName())
            .price(concertSeatEntity.getSeat().getSeatType().getPrice())
            .isAvailable(concertSeatEntity.getSeatStatus().equals(SeatStatus.AVAILABLE))
            .build();
    }

    @Override
    public ConcertSeat getConcertSeatById(final Long seatId) {
        final ConcertSeatEntity concertSeatEntity = concertSeatJpaRepository.findById(seatId)
            .orElseThrow(() -> new EntityNotFoundException("Concert seat not found. ID: " + seatId));

        return ConcertSeat.builder()
            .concertSeatId(concertSeatEntity.getId())
            .concertId(concertSeatEntity.getConcert().getId())
            .scheduleId(concertSeatEntity.getConcertSchedule().getId())
            .seatId(concertSeatEntity.getSeat().getId())
            .zoneName(concertSeatEntity.getSeat().getSeatZone().getZoneName())
            .seatType(concertSeatEntity.getSeat().getSeatType().getTypeName())
            .seatName(concertSeatEntity.getSeat().getSeatName())
            .price(concertSeatEntity.getSeat().getSeatType().getPrice())
            .isAvailable(concertSeatEntity.getSeatStatus().equals(SeatStatus.AVAILABLE))
            .build();
    }
}
