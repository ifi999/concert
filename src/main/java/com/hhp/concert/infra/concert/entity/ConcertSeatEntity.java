package com.hhp.concert.infra.concert.entity;

import com.hhp.concert.domain.SeatStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_status", nullable = false)
    private SeatStatus seatStatus;

    @Version
    private Long version;

    public ConcertSeatEntity(final ConcertEntity concert, final ConcertScheduleEntity concertSchedule, final SeatEntity seat, final SeatStatus seatStatus) {
        this.concert = concert;
        this.concertSchedule = concertSchedule;
        this.seat = seat;
        this.seatStatus = seatStatus;
    }
    @Builder
    public ConcertSeatEntity(
            final Long id,
            final ConcertEntity concert,
            final ConcertScheduleEntity concertSchedule,
            final SeatEntity seat,
            final SeatStatus seatStatus,
            final Long version
    ) {
        this.id = id;
        this.concert = concert;
        this.concertSchedule = concertSchedule;
        this.seat = seat;
        this.seatStatus = seatStatus;
        this.version = version;
    }

}
