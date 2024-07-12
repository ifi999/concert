package com.hhp.concert.infra.token.entity;

import com.hhp.concert.domain.token.TokenStatus;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "token")
public class TokenEntity {

    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private ConcertUserEntity user;

    @Column(nullable = false)
    private String token;

    @Column(name = "token_status", nullable = false)
    private TokenStatus tokenStatus;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "entry_time", nullable = false)
    private LocalDateTime entryTime;

}
