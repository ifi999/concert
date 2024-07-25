package com.hhp.concert.domain.concert;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ConcertReservation {

    private Long reservationId;
    private Long userId;
    private Long concertId;
    private Long scheduleId;
    private Long seatId;
    private String seatName;
    private Long price;
    private ConcertReservationStatus reservationStatus;
    private LocalDateTime createdAt;
    private Long version;

    public ConcertReservation(final Long userId, final Long concertId, final Long scheduleId, final Long seatId) {
        this.userId = userId;
        this.concertId = concertId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
    }

    @Builder
    public ConcertReservation(
        final Long reservationId,
        final Long userId,
        final Long concertId,
        final Long scheduleId,
        final Long seatId,
        final String seatName,
        final Long price,
        final ConcertReservationStatus reservationStatus,
        final LocalDateTime createdAt,
        final Long version
    ) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.concertId = concertId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.seatName = seatName;
        this.price = price;
        this.reservationStatus = reservationStatus;
        this.createdAt = createdAt;
        this.version = version;
    }

    public void completeReservation() {
        if (this.reservationStatus != ConcertReservationStatus.PENDING) {
            throw new IllegalStateException("Reservation status must be WAITING to complete it");
        }

        this.reservationStatus = ConcertReservationStatus.CONFIRMED;
    }

}
