package com.hhp.concert.controller.concert.dto;

import lombok.Getter;

@Getter
public class GetSeatInfoResponse {

    private Long seatId;
    private Long scheduleId;
    private Long zoneId;
    private Long typeId;
    private String seatName;

    public GetSeatInfoResponse(
        final Long seatId,
        final Long scheduleId,
        final Long zoneId,
        final Long typeId,
        final String seatName
    ) {
        this.seatId = seatId;
        this.scheduleId = scheduleId;
        this.zoneId = zoneId;
        this.typeId = typeId;
        this.seatName = seatName;
    }

}
