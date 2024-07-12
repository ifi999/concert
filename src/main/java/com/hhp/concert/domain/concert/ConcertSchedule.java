package com.hhp.concert.domain.concert;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ConcertSchedule {

    private Long concertScheduleId;
    private Long concertId;
    private LocalDate concertDate;
    private LocalDateTime concertTime;

    @Builder
    public ConcertSchedule(
        final Long concertScheduleId,
        final Long concertId,
        final LocalDate concertDate,
        final LocalDateTime concertTime
    ) {
        this.concertScheduleId = concertScheduleId;
        this.concertId = concertId;
        this.concertDate = concertDate;
        this.concertTime = concertTime;
    }

}
