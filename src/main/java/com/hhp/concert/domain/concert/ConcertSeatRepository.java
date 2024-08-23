package com.hhp.concert.domain.concert;

import java.util.List;

public interface ConcertSeatRepository {

    List<ConcertSeat> getConcertScheduleSeats(Concert concert, ConcertSchedule concertSchedule);

    ConcertSeat getSeatInfo(Concert concert, ConcertSchedule schedule, long seatId);

    ConcertSeat getConcertSeat(Long scheduleId, Long seatId);

    ConcertSeat updateSeat(ConcertSeat seat);

}
