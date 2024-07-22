package com.hhp.concert.domain.payment;

import com.hhp.concert.domain.concert.ConcertReservation;
import com.hhp.concert.domain.user.ConcertUser;

import java.util.List;

public interface PaymentRepository {

    Payment pay(ConcertUser user, ConcertReservation reservation, Long paymentAmount);

    List<Payment> getUserPayments(ConcertUser user);

}
