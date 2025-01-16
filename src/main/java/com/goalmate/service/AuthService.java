package com.goalmate.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.domain.mentee.MenteeEntity;
import com.goalmate.domain.mentee.SocialProvider;
import com.goalmate.repository.MenteeRepository;
import com.goalmate.security.jwt.JwtProvider;
import com.goalmate.security.jwt.TokenPair;
import com.goalmate.security.jwt.UserDetailsImpl;
import com.goalmate.security.oauth.oidc.OAuthMember;
import com.goalmate.security.oauth.provider.AppleUserProvider;
import com.goalmate.security.oauth.provider.KakaoUserProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
	private final AppleUserProvider appleUserProvider;
	private final KakaoUserProvider kakaoUserProvider;
	private final JwtProvider jwtProvider;
	private final MenteeRepository menteeRepository;

	public LoginResult authenticateWithOauth(String identityToken, String nonce, String provider) {
		// 소셜 제공자 매핑
		SocialProvider socialProvider = SocialProvider.valueOf(provider.toUpperCase());
		OAuthMember oauthMember = getOAuthMember(identityToken, nonce, socialProvider);

		// Mentee 조회 또는 생성
		MenteeEntity menteeEntity = menteeRepository.findBySocialId(oauthMember.subject())
			.orElseGet(() -> createNewMentee(oauthMember, socialProvider));

		// 데이터베이스에 저장
		menteeRepository.save(menteeEntity);
		log.info(">>>>>> 멤버 회원가입 또는 로그인 완료: {}", menteeEntity.getSocialId());

		// 토큰 생성 및 결과 반환
		TokenPair tokenPair = generateMenteeToken(menteeEntity);
		return new LoginResult(tokenPair.accessToken(), tokenPair.refreshToken(), menteeEntity.isPending());
	}

	private OAuthMember getOAuthMember(String identityToken, String nonce, SocialProvider socialProvider) {
		// 소셜 제공자에 따른 인증 처리
		return switch (socialProvider) {
			case APPLE -> appleUserProvider.getApplePlatformMember(identityToken, nonce);
			case KAKAO -> kakaoUserProvider.getKaKaoPlatformMember(identityToken, nonce);
			default -> throw new IllegalArgumentException("Unsupported social provider: " + socialProvider);
		};
	}

	private MenteeEntity createNewMentee(OAuthMember oauthMember, SocialProvider socialProvider) {
		// 새로운 Mentee 생성
		log.info(">>>>>> 새로운 멤버 생성: {}", oauthMember.subject());
		return MenteeEntity.builder()
			.email(oauthMember.email())
			.socialId(oauthMember.subject())
			.provider(socialProvider)
			.build();
	}

	private TokenPair generateMenteeToken(MenteeEntity menteeEntity) {
		return jwtProvider.generateTokenPair(
			UserDetailsImpl.builder()
				.id(menteeEntity.getId())
				.authorities(List.of(new SimpleGrantedAuthority(menteeEntity.getRole().getValue())))
				.build());
	}
}
