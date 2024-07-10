package com.hhp.concert.controller.concert.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetConcertResponse {

    private Long concertId;
    private String concertName;
    private String artist;
    private String venue;
    private LocalDate startDate;
    private LocalDate endDate;

    public GetConcertResponse(
        final Long concertId,
        final String concertName,
        final String artist,
        final String venue,
        final LocalDate startDate,
        final LocalDate endDate
    ) {
        this.concertId = concertId;
        this.concertName = concertName;
        this.artist = artist;
        this.venue = venue;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
