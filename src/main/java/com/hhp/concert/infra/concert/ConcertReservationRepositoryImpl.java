package com.hhp.concert.infra.concert;

import com.hhp.concert.domain.concert.*;
import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.infra.concert.entity.ConcertEntity;
import com.hhp.concert.infra.concert.entity.ConcertReservationEntity;
import com.hhp.concert.infra.concert.entity.ConcertScheduleEntity;
import com.hhp.concert.infra.concert.entity.ConcertSeatEntity;
import com.hhp.concert.infra.user.ConcertUserJpaRepository;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import com.hhp.concert.support.exception.ConcertException;
import com.hhp.concert.support.exception.ExceptionCode;
import org.springframework.stereotype.Repository;

@Repository
public class ConcertReservationRepositoryImpl implements ConcertReservationRepository {

    private final ConcertUserJpaRepository concertUserJpaRepository;
    private final ConcertJpaRepository concertJpaRepository;
    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;
    private final ConcertSeatJpaRepository concertSeatJpaRepository;
    private final ConcertReservationJpaRepository concertReservationJpaRepository;

    public ConcertReservationRepositoryImpl(
        final ConcertUserJpaRepository concertUserJpaRepository,
        final ConcertJpaRepository concertJpaRepository,
        final ConcertScheduleJpaRepository concertScheduleJpaRepository,
        final ConcertSeatJpaRepository concertSeatJpaRepository,
        final ConcertReservationJpaRepository concertReservationJpaRepository
    ) {
        this.concertUserJpaRepository = concertUserJpaRepository;
        this.concertJpaRepository = concertJpaRepository;
        this.concertScheduleJpaRepository = concertScheduleJpaRepository;
        this.concertSeatJpaRepository = concertSeatJpaRepository;
        this.concertReservationJpaRepository = concertReservationJpaRepository;
    }

    @Override
    public ConcertReservation reserve(final ConcertUser user, final ConcertSchedule schedule, final ConcertSeat seat) {
        final ConcertUserEntity userEntity = concertUserJpaRepository.findById(user.getId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.USER_NOT_FOUND));
        final ConcertEntity concertEntity = concertJpaRepository.findById(schedule.getConcertId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_NOT_FOUND));
        final ConcertScheduleEntity concertScheduleEntity = concertScheduleJpaRepository.findById(schedule.getConcertScheduleId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_SCHEDULE_NOT_FOUND));
        final ConcertSeatEntity concertSeatEntity = concertSeatJpaRepository.findById(seat.getSeatId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_SEAT_NOT_FOUND));

        final ConcertReservationEntity concertReservationEntity = ConcertReservationEntity.builder()
            .user(userEntity)
            .schedule(concertScheduleEntity)
            .concertSeat(concertSeatEntity)
            .reservationPrice(concertSeatEntity.getSeat().getSeatType().getPrice())
            .reservationStatus(ConcertReservationStatus.PENDING)
            .build();

        final ConcertReservationEntity savedConcertReservation = concertReservationJpaRepository.save(concertReservationEntity);

        return ConcertReservation.builder()
            .reservationId(savedConcertReservation.getId())
            .userId(savedConcertReservation.getUser().getId())
            .concertId(concertEntity.getId())
            .scheduleId(savedConcertReservation.getSchedule().getId())
            .seatId(savedConcertReservation.getConcertSeat().getId())
            .seatName(savedConcertReservation.getConcertSeat().getSeat().getSeatName())
            .price(savedConcertReservation.getReservationPrice())
            .reservationStatus(savedConcertReservation.getReservationStatus())
            .createdAt(savedConcertReservation.getCreatedAt())
            .version(savedConcertReservation.getVersion())
            .build();
    }

    @Override
    public ConcertReservation getReservationById(final Long reservationId) {
        final ConcertReservationEntity concertReservationEntity = concertReservationJpaRepository.findById(reservationId)
            .orElseThrow(() -> new ConcertException(ExceptionCode.RESERVATION_NOT_FOUND));

        final ConcertEntity concertEntity = concertJpaRepository.findById(concertReservationEntity.getSchedule().getConcert().getId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_NOT_FOUND));

        return ConcertReservation.builder()
            .reservationId(concertReservationEntity.getId())
            .userId(concertReservationEntity.getUser().getId())
            .concertId(concertEntity.getId())
            .scheduleId(concertReservationEntity.getSchedule().getId())
            .seatId(concertReservationEntity.getConcertSeat().getId())
            .seatName(concertReservationEntity.getConcertSeat().getSeat().getSeatName())
            .price(concertReservationEntity.getReservationPrice())
            .reservationStatus(concertReservationEntity.getReservationStatus())
            .createdAt(concertReservationEntity.getCreatedAt())
            .version(concertReservationEntity.getVersion())
            .build();
    }

    @Override
    public ConcertReservation updateConcertReservation(final ConcertReservation reservation) {
        final ConcertUserEntity userEntity = concertUserJpaRepository.findById(reservation.getUserId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_NOT_FOUND));
        final ConcertEntity concertEntity = concertJpaRepository.findById(reservation.getConcertId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_NOT_FOUND));
        final ConcertScheduleEntity concertScheduleEntity = concertScheduleJpaRepository.findById(reservation.getScheduleId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_SCHEDULE_NOT_FOUND));
        final ConcertSeatEntity concertSeatEntity = concertSeatJpaRepository.findById(reservation.getSeatId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.CONCERT_SEAT_NOT_FOUND));

        final ConcertReservationEntity reservationEntity = ConcertReservationEntity.builder()
            .id(reservation.getReservationId())
            .user(userEntity)
            .schedule(concertScheduleEntity)
            .concertSeat(concertSeatEntity)
            .reservationPrice(reservation.getPrice())
            .reservationStatus(reservation.getReservationStatus())
            .version(reservation.getVersion())
            .build();

        final ConcertReservationEntity savedReservationEntity = concertReservationJpaRepository.save(reservationEntity);

        return ConcertReservation.builder()
            .reservationId(savedReservationEntity.getId())
            .userId(savedReservationEntity.getUser().getId())
            .concertId(concertEntity.getId())
            .scheduleId(savedReservationEntity.getSchedule().getId())
            .seatId(savedReservationEntity.getConcertSeat().getId())
            .seatName(savedReservationEntity.getConcertSeat().getSeat().getSeatName())
            .price(savedReservationEntity.getReservationPrice())
            .reservationStatus(savedReservationEntity.getReservationStatus())
            .createdAt(savedReservationEntity.getCreatedAt())
            .version(savedReservationEntity.getVersion())
            .build();
    }

}
