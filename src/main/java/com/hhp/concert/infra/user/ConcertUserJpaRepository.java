package com.hhp.concert.infra.user;

import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertUserJpaRepository extends JpaRepository<ConcertUserEntity, Long> {
}
