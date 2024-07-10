package com.hhp.concert.controller.concert.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetConcertDateResponse {

    private LocalDateTime concertDate;

    public GetConcertDateResponse(final LocalDateTime concertDate) {
        this.concertDate = concertDate;
    }

}
