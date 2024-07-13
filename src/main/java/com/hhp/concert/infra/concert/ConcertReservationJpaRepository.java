package com.hhp.concert.infra.concert;

import com.hhp.concert.infra.concert.entity.ConcertReservationEntity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;

public interface ConcertReservationJpaRepository extends JpaRepository<ConcertReservationEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "2000")})
    @Query("""
        SELECT r
          FROM ConcertReservationEntity r
         WHERE r.id = :reservationId
           AND r.reservationStatus = 'PENDING' 
    """)
    Optional<ConcertReservationEntity> findByIdWithLock(Long reservationId);

}
