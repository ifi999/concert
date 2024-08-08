package com.hhp.concert.infra.token;

import com.hhp.concert.domain.token.Token;
import com.hhp.concert.domain.token.TokenRepository;
import com.hhp.concert.infra.token.entity.TokenEntity;
import com.hhp.concert.infra.user.ConcertUserJpaRepository;
import com.hhp.concert.infra.user.entity.ConcertUserEntity;
import com.hhp.concert.support.exception.ConcertException;
import com.hhp.concert.support.exception.ExceptionCode;
import com.hhp.concert.support.util.DateTimeProvider;
import com.hhp.concert.support.util.TokenProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class TokenRepositoryImpl implements TokenRepository {

    private static final String PENDING_KEY = "PENDING:";
    private static final String ACTIVE_KEY = "ACTIVE:";

    private final TokenJpaRepository tokenJpaRepository;
    private final ConcertUserJpaRepository concertUserJpaRepository;
    private final TokenProvider tokenProvider;
    private final DateTimeProvider dateTimeProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    public TokenRepositoryImpl(
        final TokenJpaRepository tokenJpaRepository,
        final ConcertUserJpaRepository concertUserJpaRepository,
        final TokenProvider tokenProvider,
        final DateTimeProvider dateTimeProvider,
        final RedisTemplate<String, Object> redisTemplate
    ) {
        this.tokenJpaRepository = tokenJpaRepository;
        this.concertUserJpaRepository = concertUserJpaRepository;
        this.tokenProvider = tokenProvider;
        this.dateTimeProvider = dateTimeProvider;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Token getTokenByUserId(final Long userId) {
        final ConcertUserEntity userEntity = concertUserJpaRepository.findById(userId)
            .orElseThrow(() -> new ConcertException(ExceptionCode.USER_NOT_FOUND));

        final LocalDateTime currentDateTime = dateTimeProvider.currentDateTime();
        final TokenEntity tokenEntity = tokenJpaRepository.findByUser(currentDateTime, currentDateTime.minusMinutes(5), userEntity)
            .orElseGet(() -> tokenJpaRepository.save(new TokenEntity(
                userEntity,
                tokenProvider.generateToken(),
                currentDateTime)
            )
        );

        redisTemplate.opsForZSet().add(PENDING_KEY, userEntity.getId(), currentDateTime.toInstant(ZoneOffset.UTC).toEpochMilli());

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
    public Token getPendingToken(final Long tokenId) {
        final LocalDateTime currentDateTime = dateTimeProvider.currentDateTime();
        final TokenEntity tokenEntity = tokenJpaRepository.findPendingToken(currentDateTime, currentDateTime.minusMinutes(5), tokenId)
            .orElseThrow(() -> new ConcertException(ExceptionCode.AUTH_TOKEN_NOT_FOUND));

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

    @Override
    public Token getPendingToken(final String token) {
        final LocalDateTime currentDateTime = dateTimeProvider.currentDateTime();
        final TokenEntity tokenEntity = tokenJpaRepository.findValidToken(currentDateTime, currentDateTime.minusMinutes(5), token)
            .orElseThrow(() -> new ConcertException(ExceptionCode.AUTH_TOKEN_NOT_FOUND));

        return Token.builder()
            .tokenId(tokenEntity.getId())
            .userId(tokenEntity.getUser().getId())
            .token(tokenEntity.getToken())
            .createdAt(tokenEntity.getCreatedAt())
            .build();
    }

    @Override
    public void updateToken(final Token token) {
        final ConcertUserEntity userEntity = concertUserJpaRepository.findById(token.getUserId())
            .orElseThrow(() -> new ConcertException(ExceptionCode.USER_NOT_FOUND));

        final TokenEntity tokenEntity = TokenEntity.builder()
            .id(token.getTokenId())
            .user(userEntity)
            .token(token.getToken())
            .lastActiveTime(token.getLastActiveTime())
            .build();

        tokenJpaRepository.save(tokenEntity);
    }

    @Override
    public Long getTokenPendingNumber(final Long userId) {
        return redisTemplate.opsForZSet().rank(PENDING_KEY, userId);
    }

    @Override
    public void activeTokens(final Integer activeRange) {
        final Set<Object> tokens = redisTemplate.opsForZSet().range(PENDING_KEY, 0, activeRange);

        if (tokens != null && !tokens.isEmpty()) {
            redisTemplate.opsForZSet().remove(PENDING_KEY, tokens.toArray());

            for (Object token : tokens) {
                redisTemplate.opsForValue().set(ACTIVE_KEY + token, token, 600, TimeUnit.SECONDS);
            }
        }

    }

}
