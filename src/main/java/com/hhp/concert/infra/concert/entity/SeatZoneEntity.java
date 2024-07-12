package com.hhp.concert.infra.concert.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "seat_zone")
public class SeatZoneEntity {

    @Id
    @Column(name = "zone_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zone_name", nullable = false)
    private String zoneName;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    public SeatZoneEntity(final String zoneName, final Integer availableSeats) {
        this.zoneName = zoneName;
        this.availableSeats = availableSeats;
    }

}
