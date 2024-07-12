package com.hhp.concert.controller.concert.dto;

import com.hhp.concert.domain.concert.ConcertReservation;
import com.hhp.concert.domain.concert.ConcertReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReserveSeatResponse {

    private long reservationId;
    private long userId;
    private long scheduleId;
    private long seatId;
    private String seatName;
    private long price;
    private ConcertReservationStatus reservationStatus;
    private LocalDateTime reservationTime;

    @Builder
    public ReserveSeatResponse(
        final long reservationId,
        final long userId,
        final long scheduleId,
        final long seatId,
        final String seatName,
        final long price,
        final ConcertReservationStatus reservationStatus,
        final LocalDateTime reservationTime
    ) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.seatName = seatName;
        this.price = price;
        this.reservationStatus = reservationStatus;
        this.reservationTime = reservationTime;
    }

    public static ReserveSeatResponse from(final ConcertReservation reservation) {
        return ReserveSeatResponse.builder()
            .reservationId(reservation.getReservationId())
            .userId(reservation.getUserId())
            .scheduleId(reservation.getScheduleId())
            .seatId(reservation.getSeatId())
            .seatName(reservation.getSeatName())
            .price(reservation.getPrice())
            .reservationStatus(reservation.getReservationStatus())
            .reservationTime(reservation.getCreatedAt())
            .build();
    }
}
