package com.goalmate.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.domain.mentee.Mentee;
import com.goalmate.domain.mentee.SocialProvider;
import com.goalmate.oauth.oidc.OAuthMember;
import com.goalmate.oauth.provider.AppleUserProvider;
import com.goalmate.oauth.provider.KakaoUserProvider;
import com.goalmate.repository.MenteeRepository;
import com.goalmate.security.JwtProvider;
import com.goalmate.security.TokenPair;
import com.goalmate.security.UserDetailsImpl;

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
		Mentee mentee = menteeRepository.findBySocialId(oauthMember.subject())
			.orElseGet(() -> createNewMentee(oauthMember, socialProvider));

		// 데이터베이스에 저장
		menteeRepository.save(mentee);
		log.info(">>>>>> 멤버 회원가입 또는 로그인 완료: {}", mentee.getSocialId());

		// 토큰 생성 및 결과 반환
		TokenPair tokenPair = generateMenteeToken(mentee);
		return new LoginResult(tokenPair.accessToken(), tokenPair.refreshToken(), mentee.isPending());
	}

	private OAuthMember getOAuthMember(String identityToken, String nonce, SocialProvider socialProvider) {
		// 소셜 제공자에 따른 인증 처리
		return switch (socialProvider) {
			case APPLE -> appleUserProvider.getApplePlatformMember(identityToken, nonce);
			case KAKAO -> kakaoUserProvider.getKaKaoPlatformMember(identityToken, nonce);
			default -> throw new IllegalArgumentException("Unsupported social provider: " + socialProvider);
		};
	}

	private Mentee createNewMentee(OAuthMember oauthMember, SocialProvider socialProvider) {
		// 새로운 Mentee 생성
		log.info(">>>>>> 새로운 멤버 생성: {}", oauthMember.subject());
		return Mentee.builder()
			.email(oauthMember.email())
			.socialId(oauthMember.subject())
			.provider(socialProvider)
			.build();
	}

	private TokenPair generateMenteeToken(Mentee mentee) {
		return jwtProvider.generateTokenPair(
			UserDetailsImpl.builder()
				.id(mentee.getId())
				.authorities(List.of(new SimpleGrantedAuthority(mentee.getRole().getValue())))
				.build());
	}
}
