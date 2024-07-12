package com.hhp.concert.infra.concert;

import com.hhp.concert.infra.concert.entity.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {

    @Query("""
        SELECT c
        FROM ConcertEntity c 
        WHERE c.endDate >= :currentDate
    """)
    List<ConcertEntity> findActiveConcerts(@Param("currentDate") LocalDate currentDate);

}
