package com.hhp.concert.controller.concert;

import com.hhp.concert.controller.concert.dto.*;
import com.hhp.concert.domain.concert.*;
import org.springframework.web.bind.annotation.*;

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
        final List<Concert> concerts = concertService.getConcerts();

        return GetConcertResponse.from(concerts);
    }

    @GetMapping("/{concertId}")
    public GetConcertInfoResponse getConcertInfo(
        @PathVariable final long concertId) {
        final Concert concert = concertService.getConcertById(concertId);

        return GetConcertInfoResponse.from(concert);
    }

    @GetMapping("/{concertId}/dates")
    public List<GetConcertDateResponse> getConcertDates(
        @PathVariable final long concertId)
    {
        final List<ConcertSchedule> concertSchedules = concertService.getConcertSchedules(concertId);

        return GetConcertDateResponse.from(concertSchedules);
    }

    @GetMapping("/{concertId}/schedules/{scheduleId}/seats")
    public List<GetConcertSeatResponse> getConcertSeats(
        @PathVariable final long concertId,
        @PathVariable final long scheduleId
    ) {
        final List<ConcertSeat> concertSeats = concertService.getConcertScheduleSeats(concertId, scheduleId);

        return GetConcertSeatResponse.from(concertSeats);
    }

    @PostMapping("/reservation")
    public ReserveSeatResponse reserve(
        @RequestBody final ReserveSeatRequest request
    ) {
        final ConcertReservation reservation = concertService.reserve(request.toDomain());

        return ReserveSeatResponse.from(reservation);
    }

    @GetMapping("/{concertId}/schedules/{scheduleId}/seats/{seatId}")
    public GetSeatInfoResponse getSeatInfo(
        @PathVariable final long concertId,
        @PathVariable final long scheduleId,
        @PathVariable final long seatId
    ) {
        final ConcertSeat concertSeat = concertService.getSeatInfo(concertId, scheduleId, seatId);

        return GetSeatInfoResponse.from(concertSeat);
    }

}
