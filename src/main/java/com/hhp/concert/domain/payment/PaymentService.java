package com.hhp.concert.domain.payment;

import com.hhp.concert.domain.concert.ConcertReservation;
import com.hhp.concert.domain.concert.ConcertReservationRepository;
import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.domain.user.ConcertUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ConcertReservationRepository concertReservationRepository;
    private final ConcertUserRepository concertUserRepository;

    public PaymentService(
        final PaymentRepository paymentRepository,
        final ConcertReservationRepository concertReservationRepository,
        final ConcertUserRepository concertUserRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.concertReservationRepository = concertReservationRepository;
        this.concertUserRepository = concertUserRepository;
    }

    public Payment pay(final Payment payment) {
        final ConcertUser user = concertUserRepository.getUserById(payment.getUserId());
        final ConcertReservation reservation = concertReservationRepository.getReservationById(payment.getReservationId());

        return paymentRepository.pay(user, reservation, payment.getPaymentAmount());
    }

}
