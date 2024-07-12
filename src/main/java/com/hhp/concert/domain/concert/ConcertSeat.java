package com.hhp.concert.domain.concert;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ConcertSeat {

    private Long concertSeatId;
    private Long concertId;
    private Long scheduleId;
    private Long seatId;
    private String zoneName;
    private String seatType;
    private Long price;

    @Builder
    public ConcertSeat(
        final Long concertSeatId,
        final Long concertId,
        final Long scheduleId,
        final Long seatId,
        final String zoneName,
        final String seatType,
        final Long price
    ) {
        this.concertSeatId = concertSeatId;
        this.concertId = concertId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.zoneName = zoneName;
        this.seatType = seatType;
        this.price = price;
    }

}
