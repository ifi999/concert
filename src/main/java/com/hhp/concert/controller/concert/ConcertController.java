package com.hhp.concert.controller.concert;

import com.hhp.concert.controller.concert.dto.GetConcertDatesResponse;
import com.hhp.concert.controller.concert.dto.GetConcertSeatsResponse;
import com.hhp.concert.controller.concert.dto.ReserveSeatRequest;
import com.hhp.concert.controller.concert.dto.ReserveSeatResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    @GetMapping("/{concertId}/available-dates")
    public List<GetConcertDatesResponse> getConcertDates(
        @PathVariable final long concertId)
    {
        return List.of(new GetConcertDatesResponse(LocalDateTime.of(2024, 7, 1, 12, 0, 0)));
    }

    @GetMapping("/{concertId}/available-seats")
    public List<GetConcertSeatsResponse> getConcertSeats(
        @PathVariable final long concertId
    ) {
        return List.of(new GetConcertSeatsResponse(1L, "A1", 30_000, "Standard"));
    }

    @PostMapping("/{concertId}/reserve-seat")
    public ReserveSeatResponse reserveSeat(
        @PathVariable final long concertId,
        @RequestBody final ReserveSeatRequest request
    ) {
        return new ReserveSeatResponse(1L, "A1", LocalDateTime.of(2024, 7, 1, 13, 0, 0), "RESERVED");
    }

}
