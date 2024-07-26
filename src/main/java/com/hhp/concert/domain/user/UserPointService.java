package com.hhp.concert.domain.user;

import jakarta.persistence.OptimisticLockException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserPointService {

    private final UserPointRepository userPointRepository;
    private final ConcertUserRepository concertUserRepository;

    public UserPointService(final UserPointRepository userPointRepository, final ConcertUserRepository concertUserRepository) {
        this.userPointRepository = userPointRepository;
        this.concertUserRepository = concertUserRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UserPoint charge(final Long userId, final UserPoint userPoint) {
        final ConcertUser user = concertUserRepository.getUserById(userId);

        final int maxRetries = 10; // 최대 재시도 횟수
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                final UserPoint remainUserPoint = userPointRepository.getUserPoint(user);
                remainUserPoint.incrementPoint(userPoint.getPoint());
                System.out.println("remainUserPoint = " + remainUserPoint);

                return userPointRepository.updateUserPoint(remainUserPoint);
            } catch (ObjectOptimisticLockingFailureException e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw new IllegalStateException("Maximum retry attempts reached.");
                }

                try {
                    // 짧은 지연 시간 설정 (예: 100밀리초)
                    Thread.sleep(100 * attempt);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // 인터럽트 상태 복원
                    throw new RuntimeException("Thread interrupted during retry delay.", ie);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        throw new IllegalStateException();
    }

    public UserPoint getBalance(final long userId) {
        final ConcertUser user = concertUserRepository.getUserById(userId);

        return userPointRepository.getBalance(user.getId());
    }

}
