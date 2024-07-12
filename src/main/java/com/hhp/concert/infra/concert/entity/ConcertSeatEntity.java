package com.hhp.concert.infra.concert.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "concert_seat")
public class ConcertSeatEntity {

    @Id
    @Column(name = "concert_seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false)
    private ConcertEntity concert;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private ConcertScheduleEntity concertSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private SeatEntity seat;

    public ConcertSeatEntity(final ConcertEntity concert, final ConcertScheduleEntity concertSchedule, final SeatEntity seat) {
        this.concert = concert;
        this.concertSchedule = concertSchedule;
        this.seat = seat;
    }

}
