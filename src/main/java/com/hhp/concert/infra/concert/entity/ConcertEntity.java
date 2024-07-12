package com.hhp.concert.infra.concert.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "concert")
public class ConcertEntity {

    @Id
    @Column(name = "concert_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "concert_name", nullable = false)
    private String concertName;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false)
    private String venue;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    public ConcertEntity(
        final String concertName,
        final String artist,
        final String venue,
        final LocalDate startDate,
        final LocalDate endDate
    ) {
        this.concertName = concertName;
        this.artist = artist;
        this.venue = venue;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Builder
    public ConcertEntity(
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

}
