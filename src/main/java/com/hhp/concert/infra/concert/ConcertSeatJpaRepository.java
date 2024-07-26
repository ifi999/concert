package com.hhp.concert.infra.concert;

import com.hhp.concert.infra.concert.entity.ConcertEntity;
import com.hhp.concert.infra.concert.entity.ConcertScheduleEntity;
import com.hhp.concert.infra.concert.entity.ConcertSeatEntity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "2000")})
    @Query("""
        SELECT s
          FROM ConcertSeatEntity s
         WHERE s.id = :seatId
           AND s.seatStatus = 'AVAILABLE'
    """)
    Optional<ConcertSeatEntity> findBySeatIdWithLock(@Param("seatId") Long seatId);

    @Lock(LockModeType.OPTIMISTIC)
    Optional<ConcertSeatEntity> findBySeatId(@Param("seatId") Long seatId);

}
