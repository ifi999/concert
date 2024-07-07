package com.hhp.concert.infra.concert.entity;

import jakarta.persistence.*;

@Entity
public class SeatZoneEntity {

    @Id
    @Column(name = "zone_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
