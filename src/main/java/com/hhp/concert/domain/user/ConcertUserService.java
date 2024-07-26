package com.hhp.concert.domain.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ConcertUserService {

    private final ConcertUserRepository concertUserRepository;
    private final UserPointRepository userPointRepository;

    public ConcertUserService(final ConcertUserRepository concertUserRepository, final UserPointRepository userPointRepository) {
        this.concertUserRepository = concertUserRepository;
        this.userPointRepository = userPointRepository;
    }

    public ConcertUser enroll(final ConcertUser user) {
        final ConcertUser enrolledUser = concertUserRepository.enroll(user);
        userPointRepository.getUserPoint(enrolledUser);

        return enrolledUser;
    }

}
