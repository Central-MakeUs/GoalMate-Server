package com.goalmate.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.domain.Mentee;
import com.goalmate.domain.SocialProvider;
import com.goalmate.oauth.apple.AppleOAuthUserProvider;
import com.goalmate.oauth.kakao.KakaoOAuthUserProvider;
import com.goalmate.oauth.oidc.OAuthMember;
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
	private final AppleOAuthUserProvider appleOAuthUserProvider;
	private final KakaoOAuthUserProvider kakaoOAuthUserProvider;
	private final JwtProvider jwtProvider;
	private final MenteeRepository menteeRepository;

	public LoginResult authenticateWithOauth(String identityToken, String nonce, String provider) {
		OAuthMember oauthMember;
		SocialProvider socialProvider = SocialProvider.valueOf(provider.toUpperCase());
		if (socialProvider == SocialProvider.APPLE) {
			oauthMember = appleOAuthUserProvider.getApplePlatformMember(identityToken, nonce);
		} else if (socialProvider == SocialProvider.KAKAO) {
			oauthMember = kakaoOAuthUserProvider.getKaKaoPlatformMember(identityToken, nonce);
		} else {
			throw new IllegalArgumentException("Unsupported social provider: " + socialProvider);
		}
		Mentee mentee = menteeRepository.findBySocialId(oauthMember.subject())
			.orElseGet(() -> Mentee.builder()
				.email(oauthMember.email())
				.socialId(oauthMember.subject())
				.provider(SocialProvider.APPLE)
				.build()
			);
		menteeRepository.save(mentee);
		log.info(">>>>>> 멤버 회원가입");
		TokenPair tokenPair = generateMenteeToken(mentee);
		return new LoginResult(tokenPair.accessToken(), tokenPair.refreshToken(), mentee.isPending());
	}

	private TokenPair generateMenteeToken(Mentee mentee) {
		return jwtProvider.generateTokenPair(
			UserDetailsImpl.builder()
				.id(mentee.getId())
				.authorities(List.of(new SimpleGrantedAuthority(mentee.getRole().getValue())))
				.build());
	}
}
