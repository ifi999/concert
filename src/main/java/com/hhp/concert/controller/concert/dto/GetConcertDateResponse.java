package com.hhp.concert.controller.concert.dto;

import com.hhp.concert.domain.concert.ConcertSchedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetConcertDateResponse {

    private Long concertScheduleId;
    private Long concertId;
    private LocalDate concertDate;
    private LocalDateTime concertTime;

    @Builder
    public GetConcertDateResponse(
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

    public static List<GetConcertDateResponse> from(final List<ConcertSchedule> concertSchedules) {
        return concertSchedules.stream()
            .map(o -> GetConcertDateResponse.builder()
                .concertScheduleId(o.getConcertScheduleId())
                .concertId(o.getConcertId())
                .concertDate(o.getConcertDate())
                .concertTime(o.getConcertTime())
                .build())
            .toList();
    }
}
