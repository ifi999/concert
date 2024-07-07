package com.hhp.concert.controller.concert.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetConcertDatesResponse {

    private LocalDateTime concertDate;

    public GetConcertDatesResponse(final LocalDateTime concertDate) {
        this.concertDate = concertDate;
    }

}
