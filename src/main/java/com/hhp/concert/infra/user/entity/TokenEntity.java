package com.hhp.concert.infra.user.entity;

import jakarta.persistence.*;

@Entity
public class TokenEntity {

    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
