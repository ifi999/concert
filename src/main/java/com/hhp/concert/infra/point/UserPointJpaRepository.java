package com.hhp.concert.infra.point;

import com.hhp.concert.infra.point.entity.UserPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointJpaRepository extends JpaRepository<UserPointEntity, Long> {
}
