package com.hhp.concert.infra.token;

import com.hhp.concert.domain.token.Token;
import com.hhp.concert.domain.token.TokenRepository;
import com.hhp.concert.domain.token.TokenStatus;
import com.hhp.concert.infra.token.entity.TokenEntity;
import com.hhp.concert.infra.user.ConcertUserJpaRepository;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import com.hhp.concert.util.DateTimeProvider;
import com.hhp.concert.util.TokenProvider;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenJpaRepository tokenJpaRepository;
    private final ConcertUserJpaRepository concertUserJpaRepository;
    private final TokenProvider tokenProvider;
    private final DateTimeProvider dateTimeProvider;

    public TokenRepositoryImpl(
        final TokenJpaRepository tokenJpaRepository,
        final ConcertUserJpaRepository concertUserJpaRepository,
        final TokenProvider tokenProvider,
        final DateTimeProvider dateTimeProvider
    ) {
        this.tokenJpaRepository = tokenJpaRepository;
        this.concertUserJpaRepository = concertUserJpaRepository;
        this.tokenProvider = tokenProvider;
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Token getTokenByUserId(final Long userId) {
        final ConcertUserEntity userEntity = concertUserJpaRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found. ID: " + userId));

        final LocalDateTime currentDateTime = dateTimeProvider.currentDateTime();
        final TokenEntity tokenEntity = tokenJpaRepository.findByUser(currentDateTime, currentDateTime.minusMinutes(5), userEntity)
            .orElseGet(() -> tokenJpaRepository.save(new TokenEntity(
                userEntity,
                tokenProvider.generateToken(),
                TokenStatus.PENDING,
                currentDateTime)
            )
        );

        final List<Long> oldestPendingTokenList = tokenJpaRepository.findOldestPendingToken(currentDateTime, currentDateTime.minusMinutes(5));
        final Long oldestPendingTokenId = oldestPendingTokenList.isEmpty() ? tokenEntity.getId() : oldestPendingTokenList.get(0);

        return Token.builder()
            .tokenId(tokenEntity.getId())
            .userId(tokenEntity.getUser().getId())
            .token(tokenEntity.getToken())
            .tokenStatus(tokenEntity.getTokenStatus())
            .createdAt(tokenEntity.getCreatedAt())
            .queueNumber(oldestPendingTokenId - tokenEntity.getId())
            .build();
    }

}
