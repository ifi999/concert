package com.hhp.concert.controller.concert.dto;

import lombok.Getter;

@Getter
public class GetConcertSeatResponse {

    private long seatId;
    private String seatName;
    private int price;
    private String zone;

    public GetConcertSeatResponse(final long seatId, final String seatName, final int price, final String zone) {
        this.seatId = seatId;
        this.seatName = seatName;
        this.price = price;
        this.zone = zone;
    }

}
