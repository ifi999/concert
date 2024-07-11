package com.hhp.concert.controller.user.dto;

import com.hhp.concert.domain.user.PointTransactionType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserPointHistory {

    private Long historyId;
    private Long pointId;
    private PointTransactionType transactionType;
    private Long amount;
    private Long remainPoint;
    private LocalDateTime createdAt;

    public GetUserPointHistory(
        final Long historyId,
        final Long pointId,
        final PointTransactionType transactionType,
        final Long amount,
        final Long remainPoint,
        final LocalDateTime createdAt
    ) {
        this.historyId = historyId;
        this.pointId = pointId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.remainPoint = remainPoint;
        this.createdAt = createdAt;
    }

}
