package com.hhp.concert.infra.point.entity;

import jakarta.persistence.*;

@Entity
public class UserPointHistoryEntity {

    @Id
    @Column(name = "history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
