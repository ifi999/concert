package com.hhp.concert.domain.user;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class UserPoint {

    private Long pointId;
    private Long userId;
    private Long point;

    public UserPoint(final Long point) {
        Assert.notNull(point, "Point must not be null.");
        Assert.isTrue(point >= 0, "Point must be greater than zero.");

        this.point = point;
    }

    public UserPoint(final Long userId, final Long point) {
        Assert.notNull(userId, "User's id must not be null.");
        Assert.isTrue(userId > 0, "User's id must be positive.");
        Assert.notNull(point, "Point must not be null.");
        Assert.isTrue(point >= 0, "Point must be greater than zero.");

        this.userId = userId;
        this.point = point;
    }

    public UserPoint(final Long pointId, final Long userId, final Long point) {
        Assert.notNull(pointId, "Point's id must not be null.");
        Assert.isTrue(pointId > 0, "Point's id must be positive.");
        Assert.notNull(userId, "User's id must not be null.");
        Assert.isTrue(userId > 0, "User's id must be positive.");
        Assert.notNull(point, "Point must not be null.");
        Assert.isTrue(point >= 0, "Point must be greater than zero.");

        this.pointId = pointId;
        this.userId = userId;
        this.point = point;
    }

    public void decrementPoint(final Long reservedPrice, final Long amount) {
        if (!reservedPrice.equals(amount)) {
            throw new IllegalArgumentException("Payment amount does not match the requested deduction amount.");
        }

        if (this.point < amount) {
            throw new IllegalArgumentException("Not enough points to deduct.");
        }

        this.point -= amount;
    }

}
