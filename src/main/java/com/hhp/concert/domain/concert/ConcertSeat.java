package com.hhp.concert.domain.concert;

import com.hhp.concert.domain.SeatStatus;
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
    private String seatName;
    private SeatStatus seatStatus;
    private Long price;
    private boolean isAvailable;

    @Builder
    public ConcertSeat(
        final Long concertSeatId,
        final Long concertId,
        final Long scheduleId,
        final Long seatId,
        final String zoneName,
        final String seatType,
        final String seatName,
        final SeatStatus seatStatus,
        final Long price,
        final boolean isAvailable
    ) {
        this.concertSeatId = concertSeatId;
        this.concertId = concertId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.zoneName = zoneName;
        this.seatType = seatType;
        this.seatName = seatName;
        this.seatStatus = seatStatus;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public void reserve() {
        this.seatStatus = SeatStatus.RESERVED;
    }

}
