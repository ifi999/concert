package com.hhp.concert.infra.payment;

import com.hhp.concert.domain.concert.ConcertReservation;
import com.hhp.concert.domain.payment.Payment;
import com.hhp.concert.domain.payment.PaymentRepository;
import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.infra.concert.ConcertReservationJpaRepository;
import com.hhp.concert.infra.concert.entity.ConcertReservationEntity;
import com.hhp.concert.infra.payment.entity.PaymentEntity;
import com.hhp.concert.infra.user.ConcertUserJpaRepository;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import com.hhp.concert.support.exception.ConcertException;
import com.hhp.concert.support.exception.ExceptionCode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final ConcertUserJpaRepository concertUserJpaRepository;
    private final ConcertReservationJpaRepository concertReservationJpaRepository;

    public PaymentRepositoryImpl(
        final PaymentJpaRepository paymentJpaRepository,
        final ConcertUserJpaRepository concertUserJpaRepository,
        final ConcertReservationJpaRepository concertReservationJpaRepository
    ) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.concertUserJpaRepository = concertUserJpaRepository;
        this.concertReservationJpaRepository = concertReservationJpaRepository;
    }

    @Override
    public Payment pay(final ConcertUser user, final ConcertReservation reservation, final Long paymentAmount) {
        final ConcertUserEntity userEntity = concertUserJpaRepository.findById(user.getId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.USER_NOT_FOUND));
        final ConcertReservationEntity concertReservationEntity = concertReservationJpaRepository.findById(reservation.getReservationId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.RESERVATION_NOT_FOUND));

        final PaymentEntity paymentEntity = paymentJpaRepository.save(new PaymentEntity(
            userEntity,
            concertReservationEntity,
            paymentAmount)
        );

        return Payment.builder()
            .paymentId(paymentEntity.getId())
            .reservationId(paymentEntity.getReservation().getId())
            .userId(paymentEntity.getUser().getId())
            .paymentAmount(paymentEntity.getPaymentPrice())
            .createdAt(paymentEntity.getCreatedAt())
            .build();
    }

    @Override
    public List<Payment> getUserPayments(final ConcertUser user) {
        final ConcertUserEntity userEntity = concertUserJpaRepository.findById(user.getId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.USER_NOT_FOUND));

        final List<PaymentEntity> paymentEntities = paymentJpaRepository.findAllByUser(userEntity);

        return paymentEntities.stream()
            .map(o -> Payment.builder()
                .paymentId(o.getId())
                .reservationId(o.getReservation().getId())
                .userId(o.getUser().getId())
                .paymentAmount(o.getPaymentPrice())
                .createdAt(o.getCreatedAt())
                .build())
            .toList();
    }
}
