package com.hhp.concert.infra.concert.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "concert_zone")
public class ConcertZoneEntity {

    @Id
    @Column(name = "zone_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private ConcertScheduleEntity concertSchedule;

    @Column(name = "zone_name", nullable = false)
    private String zoneName;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

}
