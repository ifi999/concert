package com.hhp.concert.infra.concert.entity;

import jakarta.persistence.*;

@Entity
public class ConcertScheduleEntity {

    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
