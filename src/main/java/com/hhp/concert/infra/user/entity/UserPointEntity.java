package com.hhp.concert.infra.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_point")
public class UserPointEntity {

    @Id
    @Column(name = "point_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private ConcertUserEntity user;

    @Column(nullable = false)
    private Long point;

    @Version
    private Long version;

    public UserPointEntity(final Long id, final ConcertUserEntity user, final Long point, final Long version) {
        this.id = id;
        this.user = user;
        this.point = point;
        this.version = version;
    }

    public UserPointEntity(final ConcertUserEntity user, final Long point) {
        this.user = user;
        this.point = point;
    }

    public void incrementPoint(final Long point) {
        this.point += point;
    }

}
