package com.hhp.concert.domain.concert;

import java.util.List;

public interface ConcertScheduleRepository {

    List<ConcertSchedule> getConcertSchedulesByConcertId(Concert concert);

}
