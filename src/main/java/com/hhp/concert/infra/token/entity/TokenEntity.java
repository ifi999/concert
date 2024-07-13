package com.hhp.concert.infra.token.entity;

import com.hhp.concert.domain.token.TokenStatus;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "token_status", nullable = false)
    private TokenStatus tokenStatus;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "entry_time")
    private LocalDateTime entryTime;

    @Builder
    public TokenEntity(
        final ConcertUserEntity user,
        final String token,
        final TokenStatus tokenStatus,
        final LocalDateTime createdAt
    ) {
        this.user = user;
        this.token = token;
        this.tokenStatus = tokenStatus;
        this.createdAt = createdAt;
    }

    public void validEntry(final Long oldestPendingTokenId) {
        if (oldestPendingTokenId.equals(this.id)) {
            this.tokenStatus = TokenStatus.ACTIVE;
        }
    }

}
