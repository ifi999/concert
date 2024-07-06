package com.hhp.concert.infra.payment.entity;

import jakarta.persistence.*;

@Entity
public class PaymentEntity {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
