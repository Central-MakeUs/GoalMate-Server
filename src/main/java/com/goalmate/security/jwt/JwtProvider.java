package com.goalmate.security.jwt;

import static org.springframework.util.StringUtils.*;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.goalmate.infra.redis.RefreshToken;
import com.goalmate.infra.redis.RefreshTokenRepository;
import com.goalmate.security.user.UserDetailsImpl;
import com.goalmate.support.error.CoreApiException;
import com.goalmate.support.error.ErrorType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

	private static final String AUTHENTICATION_CLAIM_NAME = "roles";
	private static final String AUTHENTICATION_SCHEME = "Bearer ";

	private final RefreshTokenRepository refreshTokenRepository;

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.access-expiry-seconds}")
	private int accessExpirySeconds;

	@Value("${jwt.refresh-expiry-seconds}")
	private int refreshExpirySeconds;

	public TokenPair generateTokenPair(Long id, String role) {
		UserDetailsImpl userDetails = UserDetailsImpl.builder()
			.id(id)
			.authorities(List.of(new SimpleGrantedAuthority(role)))
			.build();
		
		String accessToken = generateAccessToken(userDetails);
		String refreshToken = generateRefreshToken();
		refreshTokenRepository.save(new RefreshToken(refreshToken, Long.parseLong(userDetails.getUsername())));
		return new TokenPair(accessToken, refreshToken);
	}

	public String generateAccessToken(UserDetails userDetails) {
		Instant now = Instant.now();
		Instant expirationTime = now.plusSeconds(accessExpirySeconds);

		String authorities = userDetails.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		return Jwts.builder()
			.subject(userDetails.getUsername())
			.claim(AUTHENTICATION_CLAIM_NAME, authorities)
			.issuedAt(Date.from(now))
			.expiration(Date.from(expirationTime))
			.signWith(extractSecretKey())
			.compact();
	}

	public String generateRefreshToken() {
		Instant now = Instant.now();
		Instant expirationTime = now.plusSeconds(refreshExpirySeconds);

		return Jwts.builder()
			.issuedAt(Date.from(now))
			.expiration(Date.from(expirationTime))
			.signWith(extractSecretKey())
			.compact();
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = verifyAndExtractClaims(accessToken);

		Collection<? extends GrantedAuthority> authorities = null;
		if (claims.get(AUTHENTICATION_CLAIM_NAME) != null) {
			authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
				claims.get(AUTHENTICATION_CLAIM_NAME).toString());
		}

		UserDetailsImpl principal = UserDetailsImpl.builder()
			.id(Long.valueOf(claims.getSubject()))
			.authorities(authorities)
			.build();
		return new UsernamePasswordAuthenticationToken(principal, null, authorities);
	}

	public String resolveToken(String bearerToken) {
		if (hasText(bearerToken) && bearerToken.startsWith(AUTHENTICATION_SCHEME)) {
			return bearerToken.substring(AUTHENTICATION_SCHEME.length());
		}
		return null;
	}

	public void verifyToken(String token) {
		try {
			Jwts.parser()
				.verifyWith(extractSecretKey())
				.build()
				.parse(token);
		} catch (Exception e) {
			log.warn(">>>>>> Token verification failed: ", e);
			throw new CoreApiException(ErrorType.UNAUTHORIZED, e.getMessage());
		}
	}

	private Claims verifyAndExtractClaims(String accessToken) {
		try {
			return Jwts.parser()
				.verifyWith(extractSecretKey())
				.build()
				.parseSignedClaims(accessToken)
				.getPayload();
		} catch (Exception e) {
			log.warn(">>>>>> Token verification failed: ", e);
			throw new CoreApiException(ErrorType.UNAUTHORIZED, e.getMessage());
		}
	}

	private SecretKey extractSecretKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
}
