package com.hhp.concert.infra.concert.entity;

import jakarta.persistence.*;

@Entity
public class ConcertEntity {

    @Id
    @Column(name = "concert_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
