package com.hhp.concert.infra.concert.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "seat")
public class SeatEntity {

    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", nullable = false)
    private SeatZoneEntity seatZone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private SeatTypeEntity seatType;

    @Column(name = "seat_name", nullable = false)
    private String seatName;

    public SeatEntity(final SeatZoneEntity seatZone, final SeatTypeEntity seatType, final String seatName) {
        this.seatZone = seatZone;
        this.seatType = seatType;
        this.seatName = seatName;
    }

}
