package com.hhp.concert.infra.payment.entity;

import com.hhp.concert.infra.concert.entity.ConcertReservationEntity;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private ConcertUserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ConcertReservationEntity reservation;

    @Column(name = "payment_price", nullable = false)
    private Long paymentPrice;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;

    public PaymentEntity(final ConcertUserEntity user, final ConcertReservationEntity reservation, final Long paymentPrice) {
        this.user = user;
        this.reservation = reservation;
        this.paymentPrice = paymentPrice;
        this.createdAt = LocalDateTime.now();
    }

}
