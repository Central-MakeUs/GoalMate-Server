package com.goalmate.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.domain.Mentee;
import com.goalmate.domain.SocialProvider;
import com.goalmate.oauth.apple.AppleOAuthUserProvider;
import com.goalmate.oauth.apple.ApplePlatformMember;
import com.goalmate.repository.MenteeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
	private final MenteeRepository menteeRepository;
	private final AppleOAuthUserProvider appleOAuthUserProvider;

	public LoginResult authenticateWithOauth(String identityToken, String provider) {
		Mentee mentee = null;
		SocialProvider socialProvider = SocialProvider.valueOf(provider.toUpperCase());
		if (socialProvider == SocialProvider.APPLE) {
			ApplePlatformMember appleMember = appleOAuthUserProvider.getApplePlatformMember(identityToken);
			mentee = menteeRepository.findBySocialId(appleMember.subject())
				.orElseGet(() -> Mentee.builder()
					.email(appleMember.email())
					.socialId(appleMember.subject())
					.provider(SocialProvider.APPLE)
					.build()
				);
		}
		// else if (socialProvider == SocialProvider.KAKAO) {
		//
		// }
		assert mentee != null;
		return new LoginResult("accessToken", "refreshToken", mentee.isPending());
	}
}
