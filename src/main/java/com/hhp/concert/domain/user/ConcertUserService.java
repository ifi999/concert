package com.hhp.concert.domain.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ConcertUserService {

    private final ConcertUserRepository concertUserRepository;

    public ConcertUserService(final ConcertUserRepository concertUserRepository) {
        this.concertUserRepository = concertUserRepository;
    }

    public ConcertUser enroll(final ConcertUser user) {
        return concertUserRepository.enroll(user);
    }

}
