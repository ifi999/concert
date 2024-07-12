package com.hhp.concert.controller.concert.dto;

import com.hhp.concert.domain.concert.ConcertSeat;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetSeatInfoResponse {

    private Long seatId;
    private Long scheduleId;
    private String zoneName;
    private String typeName;
    private String seatName;
    private boolean isAvailable;

    @Builder
    public GetSeatInfoResponse(
        final Long seatId,
        final Long scheduleId,
        final String zoneName,
        final String typeName,
        final String seatName,
        final boolean isAvailable
    ) {
        this.seatId = seatId;
        this.scheduleId = scheduleId;
        this.zoneName = zoneName;
        this.typeName = typeName;
        this.seatName = seatName;
        this.isAvailable = isAvailable;
    }

    public static GetSeatInfoResponse from(final ConcertSeat concertSeat) {
        return GetSeatInfoResponse.builder()
            .seatId(concertSeat.getSeatId())
            .scheduleId(concertSeat.getScheduleId())
            .zoneName(concertSeat.getZoneName())
            .typeName(concertSeat.getSeatType())
            .seatName(concertSeat.getSeatName())
            .isAvailable(concertSeat.isAvailable())
            .build();
    }
}
