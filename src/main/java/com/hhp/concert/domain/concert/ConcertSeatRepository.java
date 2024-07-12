package com.hhp.concert.domain.concert;

import java.util.List;

public interface ConcertSeatRepository {

    List<ConcertSeat> getConcertScheduleSeats(Concert concert, ConcertSchedule concertSchedule);

}
