package com.hhp.concert.domain.payment;

import com.hhp.concert.domain.concert.ConcertReservation;
import com.hhp.concert.domain.concert.ConcertReservationRepository;
import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.domain.user.ConcertUserRepository;
import com.hhp.concert.domain.user.UserPoint;
import com.hhp.concert.domain.user.UserPointRepository;
import com.hhp.concert.support.exception.ConcertException;
import com.hhp.concert.support.exception.ExceptionCode;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ConcertReservationRepository concertReservationRepository;
    private final ConcertUserRepository concertUserRepository;
    private final UserPointRepository userPointRepository;

    public PaymentService(
        final PaymentRepository paymentRepository,
        final ConcertReservationRepository concertReservationRepository,
        final ConcertUserRepository concertUserRepository,
        final UserPointRepository userPointRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.concertReservationRepository = concertReservationRepository;
        this.concertUserRepository = concertUserRepository;
        this.userPointRepository = userPointRepository;
    }

    public Payment pay(final Payment payment) {
        final ConcertUser user = concertUserRepository.getUserById(payment.getUserId());
        final UserPoint userPoint = userPointRepository.getUserPointByUserId(payment.getUserId());
        final ConcertReservation reservation = concertReservationRepository.getReservationById(payment.getReservationId());

        userPoint.decrementPoint(reservation.getPrice(), payment.getPaymentAmount());
        reservation.completeReservation();

        try {
            userPointRepository.updateUserPoint(userPoint);
            final ConcertReservation updatedReservation = concertReservationRepository.updateConcertReservation(reservation);

            return paymentRepository.pay(user, updatedReservation, payment.getPaymentAmount());
        } catch (OptimisticLockException e) {
            throw new ConcertException(ExceptionCode.PAY_FAILED);
        } catch (Exception e) {
            throw new ConcertException(ExceptionCode.PAY_FAILED);
        }
    }

    public List<Payment> getUserPayments(final long userId) {
        final ConcertUser user = concertUserRepository.getUserById(userId);

        return paymentRepository.getUserPayments(user);
    }

}
