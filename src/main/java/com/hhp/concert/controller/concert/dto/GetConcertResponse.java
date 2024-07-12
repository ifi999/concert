package com.hhp.concert.controller.concert.dto;

import com.hhp.concert.domain.concert.Concert;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class GetConcertResponse {

    private Long concertId;
    private String concertName;
    private String artist;
    private String venue;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
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

    public static List<GetConcertResponse> from(final List<Concert> concerts) {
        return concerts.stream()
            .map(o -> GetConcertResponse.builder()
                .concertId(o.getId())
                .concertName(o.getConcertName())
                .artist(o.getArtist())
                .venue(o.getVenue())
                .startDate(o.getStartDate())
                .endDate(o.getEndDate())
                .build()
            )
            .toList();
    }

}
