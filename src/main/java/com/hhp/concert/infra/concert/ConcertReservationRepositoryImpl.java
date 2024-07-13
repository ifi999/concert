package com.hhp.concert.infra.concert;

import com.hhp.concert.domain.concert.*;
import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.infra.concert.entity.ConcertReservationEntity;
import com.hhp.concert.infra.concert.entity.ConcertScheduleEntity;
import com.hhp.concert.infra.concert.entity.ConcertSeatEntity;
import com.hhp.concert.infra.user.ConcertUserJpaRepository;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class ConcertReservationRepositoryImpl implements ConcertReservationRepository {

    private final ConcertUserJpaRepository concertUserJpaRepository;
    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;
    private final ConcertSeatJpaRepository concertSeatJpaRepository;
    private final ConcertReservationJpaRepository concertReservationJpaRepository;

    public ConcertReservationRepositoryImpl(
        final ConcertUserJpaRepository concertUserJpaRepository,
        final ConcertScheduleJpaRepository concertScheduleJpaRepository,
        final ConcertSeatJpaRepository concertSeatJpaRepository,
        final ConcertReservationJpaRepository concertReservationJpaRepository
    ) {
        this.concertUserJpaRepository = concertUserJpaRepository;
        this.concertScheduleJpaRepository = concertScheduleJpaRepository;
        this.concertSeatJpaRepository = concertSeatJpaRepository;
        this.concertReservationJpaRepository = concertReservationJpaRepository;
    }

    @Override
    public ConcertReservation reserve(final ConcertUser user, final ConcertSchedule schedule, final ConcertSeat seat) {
        final ConcertUserEntity userEntity = concertUserJpaRepository.findById(user.getId())
            .orElseThrow(() -> new EntityNotFoundException("User not found. ID: " + user.getId()));
        final ConcertScheduleEntity concertScheduleEntity = concertScheduleJpaRepository.findById(schedule.getConcertScheduleId())
            .orElseThrow(() -> new EntityNotFoundException("Concert's schedule not found. ID: " + schedule.getConcertScheduleId()));
        final ConcertSeatEntity concertSeatEntity = concertSeatJpaRepository.findBySeatIdWithLock(seat.getSeatId())
            .orElseThrow(() -> new EntityNotFoundException("Concert's seat not found. ID: " + seat.getSeatId()));

        concertSeatEntity.reserve();

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
            .scheduleId(savedConcertReservation.getSchedule().getId())
            .seatId(savedConcertReservation.getConcertSeat().getId())
            .seatName(savedConcertReservation.getConcertSeat().getSeat().getSeatName())
            .price(savedConcertReservation.getReservationPrice())
            .reservationStatus(savedConcertReservation.getReservationStatus())
            .createdAt(savedConcertReservation.getCreatedAt())
            .build();
    }

    @Override
    public ConcertReservation getReservationById(final Long reservationId) {
        final ConcertReservationEntity concertReservationEntity = concertReservationJpaRepository.findById(reservationId)
            .orElseThrow(() -> new EntityNotFoundException("Concert's reservation not found. ID: " + reservationId));

        return ConcertReservation.builder()
            .reservationId(concertReservationEntity.getId())
            .userId(concertReservationEntity.getUser().getId())
            .scheduleId(concertReservationEntity.getSchedule().getId())
            .seatId(concertReservationEntity.getConcertSeat().getId())
            .seatName(concertReservationEntity.getConcertSeat().getSeat().getSeatName())
            .price(concertReservationEntity.getReservationPrice())
            .reservationStatus(concertReservationEntity.getReservationStatus())
            .createdAt(concertReservationEntity.getCreatedAt())
            .build();
    }

}
