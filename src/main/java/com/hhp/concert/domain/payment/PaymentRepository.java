package com.hhp.concert.domain.payment;

import com.hhp.concert.domain.concert.ConcertReservation;
import com.hhp.concert.domain.user.ConcertUser;

public interface PaymentRepository {

    Payment pay(ConcertUser user, ConcertReservation reservation, Long paymentAmount);

}
