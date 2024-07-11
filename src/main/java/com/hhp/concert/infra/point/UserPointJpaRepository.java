package com.hhp.concert.infra.point;

import com.hhp.concert.infra.point.entity.UserPointEntity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserPointJpaRepository extends JpaRepository<UserPointEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "2000")})
    @Query("""
        SELECT p
          FROM UserPointEntity p
         WHERE p.user.id = :userId
    """)
    Optional<UserPointEntity> findByUserIdWithLock(@Param("userId") Long userId);

    Optional<UserPointEntity> findByUserId(Long userId);

}
