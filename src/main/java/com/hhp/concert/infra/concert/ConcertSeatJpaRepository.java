package com.hhp.concert.infra.concert;

import com.hhp.concert.infra.concert.entity.ConcertEntity;
import com.hhp.concert.infra.concert.entity.ConcertScheduleEntity;
import com.hhp.concert.infra.concert.entity.ConcertSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {


    @Query("""
        SELECT s
          FROM ConcertSeatEntity s
         WHERE s.concert = :concert
           AND s.concertSchedule = :schedule
           AND s.seatStatus = 'AVAILABLE'
    """)
    List<ConcertSeatEntity> findAvailableConcertSeats(
        @Param("concert") ConcertEntity concert,
        @Param("schedule") ConcertScheduleEntity schedule
    );

    @Query("""
        SELECT s
          FROM ConcertSeatEntity s
         WHERE s.concert = :concert
           AND s.concertSchedule = :schedule
           AND s.id = :seatId
    """)
    Optional<ConcertSeatEntity> findConcertSeatInfo(
        @Param("concert") ConcertEntity concert,
        @Param("schedule") ConcertScheduleEntity schedule,
        @Param("seatId") Long seatId
    );

}
