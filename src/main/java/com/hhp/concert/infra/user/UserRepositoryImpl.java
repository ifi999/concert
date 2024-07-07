package com.hhp.concert.infra.user;

import com.hhp.concert.domain.user.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(final UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

}
