package com.hhp.concert.infra.concert.entity;

import com.hhp.concert.domain.concert.ConcertReservationStatus;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "concert_reservation")
public class ConcertReservationEntity {

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private ConcertUserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private ConcertScheduleEntity schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private ConcertSeatEntity concertSeat;

    @Column(name = "reservation_price", nullable = false)
    private Long reservationPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status", nullable = false)
    private ConcertReservationStatus reservationStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ConcertReservationEntity(
        final ConcertUserEntity user,
        final ConcertScheduleEntity schedule,
        final ConcertSeatEntity concertSeat,
        final Long reservationPrice,
        final ConcertReservationStatus reservationStatus
    ) {
        this.user = user;
        this.schedule = schedule;
        this.concertSeat = concertSeat;
        this.reservationPrice = reservationPrice;
        this.reservationStatus = reservationStatus;
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public ConcertReservationEntity(
        final Long id,
        final ConcertUserEntity user,
        final ConcertScheduleEntity schedule,
        final ConcertSeatEntity concertSeat,
        final Long reservationPrice,
        final ConcertReservationStatus reservationStatus
    ) {
        this.id = id;
        this.user = user;
        this.schedule = schedule;
        this.concertSeat = concertSeat;
        this.reservationPrice = reservationPrice;
        this.reservationStatus = reservationStatus;
        this.createdAt = LocalDateTime.now();
    }

}
