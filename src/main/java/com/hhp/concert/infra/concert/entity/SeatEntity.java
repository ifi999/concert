package com.hhp.concert.infra.concert.entity;

import jakarta.persistence.*;

@Entity
public class SeatEntity {

    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
