package com.hhp.concert.infra.concert.entity;

import jakarta.persistence.*;

@Entity
public class SeatReservationEntity {

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
