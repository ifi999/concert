package com.hhp.concert.infra.point.entity;

import com.hhp.concert.domain.point.PointTransactionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_point_history")
public class UserPointHistoryEntity {

    @Id
    @Column(name = "history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private UserPointEntity userPoint;

    @Column(name = "transaction_type", nullable = false)
    private PointTransactionType transactionType;

    @Column(nullable = false)
    private Long amount;

    @Column(name = "remain_point", nullable = false)
    private Long remainPoint;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
