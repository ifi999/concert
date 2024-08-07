package com.hhp.concert.domain.concert;

import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class Concert implements Serializable {

    @Serial
    private static final long serialVersionUID = 6198714102669185331L;

    private Long id;
    private String concertName;
    private String artist;
    private String venue;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public Concert(
        final Long id,
        final String concertName,
        final String artist,
        final String venue,
        final LocalDate startDate,
        final LocalDate endDate
    ) {
        this.id = id;
        this.concertName = concertName;
        this.artist = artist;
        this.venue = venue;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Concert{" +
                "id=" + id +
                ", concertName='" + concertName + '\'' +
                ", artist='" + artist + '\'' +
                ", venue='" + venue + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

}
