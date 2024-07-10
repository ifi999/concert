package com.hhp.concert.controller.seat;

import com.hhp.concert.controller.concert.dto.ReserveSeatRequest;
import com.hhp.concert.controller.concert.dto.ReserveSeatResponse;
import com.hhp.concert.controller.seat.dto.GetSeatInfoResponse;
import com.hhp.concert.domain.seat.SeatStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class SeatController {

    @PostMapping("/reservation")
    public ReserveSeatResponse reserve(
        @RequestBody final ReserveSeatRequest request
    ) {
        return new ReserveSeatResponse(1L, "A1", LocalDateTime.of(2024, 7, 1, 13, 0, 0), "RESERVED");
    }

    @GetMapping("/seats/{seatId}")
    public GetSeatInfoResponse getSeatInfo(
        @PathVariable final long seatId
    ) {
        return new GetSeatInfoResponse(
            12L,
            34L,
            56L,
            78L,
            "A-11",
            SeatStatus.AVAILABLE
        );
    }

}
