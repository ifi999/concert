package com.hhp.concert.infra.token.entity;

import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "entry_time")
    private LocalDateTime entryTime;

    @Column(name = "last_active_time")
    private LocalDateTime lastActiveTime;

    public TokenEntity(
        final ConcertUserEntity user,
        final String token,
        final LocalDateTime createdAt
    ) {
        this.user = user;
        this.token = token;
        this.createdAt = createdAt;
    }

    @Builder
    public TokenEntity(
        final Long id,
        final ConcertUserEntity user,
        final String token,
        final LocalDateTime lastActiveTime
    ) {
        this.id = id;
        this.user = user;
        this.token = token;
        this.lastActiveTime = lastActiveTime;
    }

}
