package com.hhp.concert.infra.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "concert_user")
public class ConcertUserEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
