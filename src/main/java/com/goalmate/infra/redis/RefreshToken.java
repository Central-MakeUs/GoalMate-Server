package com.goalmate.infra.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 12 * 60 * 60)
public class RefreshToken {

	@Id
	private String refreshToken;

	private Long menteeId;

	public RefreshToken(String refreshToken, Long menteeId) {
		this.refreshToken = refreshToken;
		this.menteeId = menteeId;
	}
}
