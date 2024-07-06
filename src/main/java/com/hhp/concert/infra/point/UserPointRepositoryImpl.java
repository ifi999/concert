package com.hhp.concert.infra.point;

import com.hhp.concert.domain.point.UserPointRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserPointRepositoryImpl implements UserPointRepository {

    private UserPointJpaRepository userPointJpaRepository;

    public UserPointRepositoryImpl(final UserPointJpaRepository userPointJpaRepository) {
        this.userPointJpaRepository = userPointJpaRepository;
    }

}
