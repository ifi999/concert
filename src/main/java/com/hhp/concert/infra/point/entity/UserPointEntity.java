package com.hhp.concert.infra.point.entity;

import jakarta.persistence.*;

@Entity
public class UserPointEntity {

    @Id
    @Column(name = "point_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
