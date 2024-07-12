package com.hhp.concert.controller.concert.dto;

import com.hhp.concert.domain.concert.ConcertSeat;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetConcertSeatResponse {

    private long seatId;
    private String seatName;
    private long price;
    private String zoneName;

    @Builder
    public GetConcertSeatResponse(final long seatId, final String seatName, final long price, final String zoneName) {
        this.seatId = seatId;
        this.seatName = seatName;
        this.price = price;
        this.zoneName = zoneName;
    }

    public static List<GetConcertSeatResponse> from(final List<ConcertSeat> concertSeats) {
        return concertSeats.stream()
            .map(o -> GetConcertSeatResponse.builder()
                .seatId(o.getSeatId())
                .seatName(o.getSeatType())
                .price(o.getPrice())
                .zoneName(o.getZoneName())
                .build())
            .toList();
    }
}
