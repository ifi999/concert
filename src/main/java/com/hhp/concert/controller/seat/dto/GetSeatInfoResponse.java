package com.hhp.concert.controller.seat.dto;

import com.hhp.concert.domain.seat.SeatStatus;
import lombok.Getter;

@Getter
public class GetSeatInfoResponse {

    private Long seatId;
    private Long scheduleId;
    private Long zoneId;
    private Long typeId;
    private String seatName;
    private SeatStatus seatStatus;

    public GetSeatInfoResponse(
        final Long seatId,
        final Long scheduleId,
        final Long zoneId,
        final Long typeId,
        final String seatName,
        final SeatStatus seatStatus
    ) {
        this.seatId = seatId;
        this.scheduleId = scheduleId;
        this.zoneId = zoneId;
        this.typeId = typeId;
        this.seatName = seatName;
        this.seatStatus = seatStatus;
    }

}
