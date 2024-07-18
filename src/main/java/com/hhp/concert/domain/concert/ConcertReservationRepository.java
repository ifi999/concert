package com.hhp.concert.domain.concert;

import com.hhp.concert.domain.user.ConcertUser;

public interface ConcertReservationRepository {

    ConcertReservation reserve(ConcertUser user, ConcertSchedule schedule, ConcertSeat seat);

    ConcertReservation getReservationById(Long reservationId);

    ConcertReservation updateConcertReservation(ConcertReservation reservation);

}
