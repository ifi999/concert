package com.hhp.concert.infra.user;

import com.hhp.concert.domain.user.ConcertUser;
import com.hhp.concert.domain.user.ConcertUserRepository;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class ConcertUserRepositoryImpl implements ConcertUserRepository {

    private final ConcertUserJpaRepository concertUserJpaRepository;

    public ConcertUserRepositoryImpl(final ConcertUserJpaRepository concertUserJpaRepository) {
        this.concertUserJpaRepository = concertUserJpaRepository;
    }

    @Override
    public ConcertUser enroll(final ConcertUser user) {
        final ConcertUserEntity savedUser = concertUserJpaRepository.save(new ConcertUserEntity(user.getName(), user.getEmail()));

        return new ConcertUser(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    @Override
    public ConcertUser getUserById(final Long userId) {
        final ConcertUserEntity userEntity = concertUserJpaRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found. ID: " + userId));

        return new ConcertUser(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }

}
