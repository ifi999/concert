package com.hhp.concert.infra.payment;

import com.hhp.concert.domain.concert.ConcertReservation;
import com.hhp.concert.domain.payment.Payment;
import com.hhp.concert.domain.payment.PaymentRepository;
import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.infra.concert.ConcertReservationJpaRepository;
import com.hhp.concert.infra.concert.entity.ConcertReservationEntity;
import com.hhp.concert.infra.payment.entity.PaymentEntity;
import com.hhp.concert.infra.user.ConcertUserJpaRepository;
import com.hhp.concert.infra.user.UserPointJpaRepository;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import com.hhp.concert.infra.user.entity.UserPointEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final ConcertUserJpaRepository concertUserJpaRepository;
    private final ConcertReservationJpaRepository concertReservationJpaRepository;
    private final UserPointJpaRepository userPointJpaRepository;

    public PaymentRepositoryImpl(
        final PaymentJpaRepository paymentJpaRepository,
        final ConcertUserJpaRepository concertUserJpaRepository,
        final ConcertReservationJpaRepository concertReservationJpaRepository,
        final UserPointJpaRepository userPointJpaRepository
    ) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.concertUserJpaRepository = concertUserJpaRepository;
        this.concertReservationJpaRepository = concertReservationJpaRepository;
        this.userPointJpaRepository = userPointJpaRepository;
    }

    @Override
    public Payment pay(final ConcertUser user, final ConcertReservation reservation, final Long paymentAmount) {
        final ConcertUserEntity userEntity = concertUserJpaRepository.findById(user.getId())
            .orElseThrow(() -> new EntityNotFoundException("User not found. ID: " + user.getId()));
        final UserPointEntity userPointEntity = userPointJpaRepository.findByUserIdWithLock(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User's point not found. ID: " + user.getId()));

        final ConcertReservationEntity concertReservationEntity = concertReservationJpaRepository.findByIdWithLock(reservation.getReservationId())
            .orElseThrow(() -> new EntityNotFoundException("Concert's reservation not found. ID: " + reservation.getReservationId()));

        userPointEntity.decrementPoint(concertReservationEntity.getReservationPrice(), paymentAmount);
        concertReservationEntity.completeReservation();

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

}
