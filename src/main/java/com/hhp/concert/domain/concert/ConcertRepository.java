package com.hhp.concert.domain.concert;

import com.hhp.concert.infra.concert.entity.ConcertEntity;

import java.time.LocalDate;
import java.util.List;

public interface ConcertRepository {

    List<ConcertEntity> getConcerts(LocalDate currentDate);

    Concert getConcertById(long concertId);

}
