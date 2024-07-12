package com.hhp.concert.controller.concert;

import com.hhp.concert.controller.concert.dto.*;
import com.hhp.concert.domain.concert.Concert;
import com.hhp.concert.domain.concert.ConcertService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertService concertService;

    public ConcertController(final ConcertService concertService) {
        this.concertService = concertService;
    }

    @GetMapping
    public List<GetConcertResponse> getConcerts() {
        List<Concert> concerts = concertService.getConcerts();

        return List.of(new GetConcertResponse(
            1L,
            "콘서트",
            "가수",
            "장소",
            LocalDate.of(2024, 12, 10),
            LocalDate.of(2024, 12, 11))
        );
    }

    @GetMapping("/{concertId}")
    public GetConcertInfoResponse getConcertInfo(
        @PathVariable final String concertId) {
        return new GetConcertInfoResponse(
            1L,
            "콘서트",
            "가수",
            "장소",
            LocalDate.of(2024, 12, 10),
            LocalDate.of(2024, 12, 11)
        );
    }

    @GetMapping("/{concertId}/dates")
    public List<GetConcertDateResponse> getConcertDates(
        @PathVariable final long concertId)
    {
        return List.of(new GetConcertDateResponse(LocalDateTime.of(2024, 7, 1, 12, 0, 0)));
    }

    @GetMapping("/{concertId}/dates/{date}/seats")
    public List<GetConcertSeatResponse> getConcertSeats(
        @PathVariable final long concertId,
        @PathVariable final LocalDate date
    ) {
        return List.of(new GetConcertSeatResponse(1L, "A1", 30_000, "Standard"));
    }

    @PostMapping("/reservation")
    public ReserveSeatResponse reserve(
            @RequestBody final ReserveSeatRequest request
    ) {
        return new ReserveSeatResponse(1L, "A1", LocalDateTime.of(2024, 7, 1, 13, 0, 0), "RESERVED");
    }

    @GetMapping("/{concertId}/seats/{seatId}")
    public GetSeatInfoResponse getSeatInfo(
            @PathVariable final long seatId
    ) {
        return new GetSeatInfoResponse(
                12L,
                34L,
                56L,
                78L,
                "A-11"
        );
    }

}
