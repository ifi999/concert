package com.hhp.concert.infra.token;

import com.hhp.concert.domain.token.Token;
import com.hhp.concert.domain.token.TokenRepository;
import com.hhp.concert.infra.token.entity.TokenEntity;
import com.hhp.concert.infra.user.ConcertUserJpaRepository;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import com.hhp.concert.support.util.DateTimeProvider;
import com.hhp.concert.support.util.TokenProvider;
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
                currentDateTime)
            )
        );

        return Token.builder()
            .tokenId(tokenEntity.getId())
            .userId(tokenEntity.getUser().getId())
            .token(tokenEntity.getToken())
            .createdAt(tokenEntity.getCreatedAt())
            .build();
    }

    @Override
    public List<Long> getOldestPendingToken() {
        final LocalDateTime currentDateTime = dateTimeProvider.currentDateTime();

        return tokenJpaRepository.findOldestPendingToken(currentDateTime, currentDateTime.minusMinutes(5));
    }

    @Override
    public Token findPendingToken(final Long tokenId) {
        final LocalDateTime currentDateTime = dateTimeProvider.currentDateTime();
        final TokenEntity tokenEntity = tokenJpaRepository.findPendingToken(currentDateTime, currentDateTime.minusMinutes(5), tokenId)
            .orElseThrow(() -> new EntityNotFoundException("Token not found. ID: " + tokenId));

        return Token.builder()
            .tokenId(tokenEntity.getId())
            .userId(tokenEntity.getUser().getId())
            .token(tokenEntity.getToken())
            .createdAt(tokenEntity.getCreatedAt())
            .build();
    }

    @Override
    public boolean auth(final String token) {
        final LocalDateTime currentDateTime = dateTimeProvider.currentDateTime();

        return tokenJpaRepository.findValidToken(currentDateTime, currentDateTime.minusMinutes(5), token).isPresent();
    }

}
