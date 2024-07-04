package com.hhp.concert.controller.concert.dto;

import lombok.Getter;

@Getter
public class GetConcertSeatsResponse {

    private long seatId;
    private String seatName;
    private int price;
    private String zone;

    public GetConcertSeatsResponse(final long seatId, final String seatName, final int price, final String zone) {
        this.seatId = seatId;
        this.seatName = seatName;
        this.price = price;
        this.zone = zone;
    }

}
