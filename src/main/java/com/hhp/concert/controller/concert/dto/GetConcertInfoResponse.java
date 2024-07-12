package com.hhp.concert.controller.concert.dto;

import com.hhp.concert.domain.concert.Concert;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetConcertInfoResponse {

    private Long concertId;
    private String concertName;
    private String artist;
    private String venue;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public GetConcertInfoResponse(
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

    public static GetConcertInfoResponse from(final Concert concert) {
        return GetConcertInfoResponse.builder()
            .concertId(concert.getId())
            .concertName(concert.getConcertName())
            .artist(concert.getArtist())
            .venue(concert.getVenue())
            .startDate(concert.getStartDate())
            .endDate(concert.getEndDate())
            .build();
    }

}
