package com.hhp.concert.domain.concert;

import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ConcertSchedule implements Serializable {

    @Serial
    private static final long serialVersionUID = 7469361348668608465L;

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
