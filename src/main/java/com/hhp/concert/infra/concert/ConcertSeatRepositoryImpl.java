package com.hhp.concert.infra.concert;

import com.hhp.concert.domain.SeatStatus;
import com.hhp.concert.domain.concert.Concert;
import com.hhp.concert.domain.concert.ConcertSchedule;
import com.hhp.concert.domain.concert.ConcertSeat;
import com.hhp.concert.domain.concert.ConcertSeatRepository;
import com.hhp.concert.infra.concert.entity.ConcertEntity;
import com.hhp.concert.infra.concert.entity.ConcertScheduleEntity;
import com.hhp.concert.infra.concert.entity.ConcertSeatEntity;
import com.hhp.concert.infra.concert.entity.SeatEntity;
import com.hhp.concert.support.exception.ConcertException;
import com.hhp.concert.support.exception.ExceptionCode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConcertSeatRepositoryImpl implements ConcertSeatRepository {

    private final ConcertJpaRepository concertJpaRepository;
    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;
    private final ConcertSeatJpaRepository concertSeatJpaRepository;
    private final SeatJpaRepository seatJpaRepository;

    public ConcertSeatRepositoryImpl(
        final ConcertJpaRepository concertJpaRepository,
        final ConcertScheduleJpaRepository concertScheduleJpaRepository,
        final ConcertSeatJpaRepository concertSeatJpaRepository,
        final SeatJpaRepository seatJpaRepository
    ) {
        this.concertJpaRepository = concertJpaRepository;
        this.concertScheduleJpaRepository = concertScheduleJpaRepository;
        this.concertSeatJpaRepository = concertSeatJpaRepository;
        this.seatJpaRepository = seatJpaRepository;
    }

    @Override
    public List<ConcertSeat> getConcertScheduleSeats(final Concert concert, final ConcertSchedule schedule) {
        final ConcertEntity concertEntity = concertJpaRepository.findById(concert.getId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_NOT_FOUND));

        final ConcertScheduleEntity concertScheduleEntity = concertScheduleJpaRepository.findById(schedule.getConcertScheduleId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_SCHEDULE_NOT_FOUND));

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
                .version(o.getVersion())
                .build())
            .toList();
    }

    @Override
    public ConcertSeat getSeatInfo(final Concert concert, final ConcertSchedule schedule, final long seatId) {
        final ConcertEntity concertEntity = concertJpaRepository.findById(concert.getId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_NOT_FOUND));

        final ConcertScheduleEntity concertScheduleEntity = concertScheduleJpaRepository.findById(schedule.getConcertScheduleId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_SCHEDULE_NOT_FOUND));

        final ConcertSeatEntity concertSeatEntity = concertSeatJpaRepository.findConcertSeatInfo(concertEntity, concertScheduleEntity, seatId)
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_SEAT_NOT_FOUND));

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
            .version(concertSeatEntity.getVersion())
            .build();
    }

    @Override
    public ConcertSeat getConcertSeat(final Long scheduleId, final Long seatId) {
        final ConcertSeatEntity concertSeatEntity = concertSeatJpaRepository.findByScheduleIdAndSeatId(scheduleId, seatId)
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_SEAT_NOT_FOUND));

        return ConcertSeat.builder()
            .concertSeatId(concertSeatEntity.getId())
            .concertId(concertSeatEntity.getConcert().getId())
            .scheduleId(concertSeatEntity.getConcertSchedule().getId())
            .seatId(concertSeatEntity.getSeat().getId())
            .zoneName(concertSeatEntity.getSeat().getSeatZone().getZoneName())
            .seatType(concertSeatEntity.getSeat().getSeatType().getTypeName())
            .seatName(concertSeatEntity.getSeat().getSeatName())
            .seatStatus(concertSeatEntity.getSeatStatus())
            .price(concertSeatEntity.getSeat().getSeatType().getPrice())
            .isAvailable(concertSeatEntity.getSeatStatus().equals(SeatStatus.AVAILABLE))
            .version(concertSeatEntity.getVersion())
            .build();
    }

    @Override
    public ConcertSeat updateSeat(final ConcertSeat seat) {
        final ConcertEntity concertEntity = concertJpaRepository.findById(seat.getConcertId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_NOT_FOUND));

        final ConcertScheduleEntity concertScheduleEntity = concertScheduleJpaRepository.findById(seat.getScheduleId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_SCHEDULE_NOT_FOUND));

        final SeatEntity seatEntity = seatJpaRepository.findById(seat.getSeatId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.SEAT_NOT_FOUND));

        final ConcertSeatEntity concertSeatEntity = ConcertSeatEntity.builder()
                .id(seat.getConcertSeatId())
                .concert(concertEntity)
                .concertSchedule(concertScheduleEntity)
                .seat(seatEntity)
                .seatStatus(seat.getSeatStatus())
                .version(seat.getVersion())
                .build();

        final ConcertSeatEntity savedConcertSeatEntity = concertSeatJpaRepository.save(concertSeatEntity);

        return ConcertSeat.builder()
            .concertSeatId(savedConcertSeatEntity.getId())
            .concertId(savedConcertSeatEntity.getConcert().getId())
            .scheduleId(savedConcertSeatEntity.getConcertSchedule().getId())
            .seatId(savedConcertSeatEntity.getSeat().getId())
            .zoneName(savedConcertSeatEntity.getSeat().getSeatZone().getZoneName())
            .seatType(savedConcertSeatEntity.getSeat().getSeatType().getTypeName())
            .seatName(savedConcertSeatEntity.getSeat().getSeatName())
            .seatStatus(savedConcertSeatEntity.getSeatStatus())
            .price(savedConcertSeatEntity.getSeat().getSeatType().getPrice())
            .isAvailable(savedConcertSeatEntity.getSeatStatus().equals(SeatStatus.RESERVED))
            .version(savedConcertSeatEntity.getVersion())
            .build();
    }

}
