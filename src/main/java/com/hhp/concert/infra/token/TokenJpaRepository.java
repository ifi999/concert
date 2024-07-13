package com.hhp.concert.infra.token;

import com.hhp.concert.infra.token.entity.TokenEntity;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenJpaRepository extends JpaRepository<TokenEntity, Long> {

    @Query("""
        SELECT t
          FROM TokenEntity t
         WHERE t.user = :user
           AND t.createdAt <= :now
           AND t.createdAt >= :fiveMinutesAgo
           AND t.tokenStatus = 'PENDING'
    """)
    Optional<TokenEntity> findByUser(
        @Param("now") LocalDateTime now,
        @Param("fiveMinutesAgo") LocalDateTime fiveMinutesAgo,
        @Param("user") ConcertUserEntity user
    );

    @Query("""
        SELECT t.id
          FROM TokenEntity t
         WHERE t.tokenStatus = 'PENDING'
           AND t.createdAt <= :now
           AND t.createdAt >= :fiveMinutesAgo
         ORDER By t.createdAt
    """)
    List<Long> findOldestPendingToken(@Param("now") LocalDateTime now, @Param("fiveMinutesAgo") LocalDateTime fiveMinutesAgo);

}
