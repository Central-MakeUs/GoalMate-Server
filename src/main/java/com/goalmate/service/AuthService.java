package com.goalmate.service;

import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.api.model.LoginAdminRequest;
import com.goalmate.api.model.LoginMentorRequest;
import com.goalmate.domain.admin.AdminEntity;
import com.goalmate.domain.mentee.MenteeEntity;
import com.goalmate.domain.mentee.SocialProvider;
import com.goalmate.domain.mentor.MentorEntity;
import com.goalmate.infra.redis.RefreshToken;
import com.goalmate.infra.redis.RefreshTokenRepository;
import com.goalmate.repository.AdminRepository;
import com.goalmate.repository.MenteeRepository;
import com.goalmate.repository.MentorRepository;
import com.goalmate.security.jwt.JwtProvider;
import com.goalmate.security.jwt.LoginResult;
import com.goalmate.security.jwt.TokenPair;
import com.goalmate.security.oauth.oidc.OAuthMember;
import com.goalmate.security.oauth.provider.AppleUserProvider;
import com.goalmate.security.oauth.provider.KakaoUserProvider;
import com.goalmate.security.user.CurrentUserContext;
import com.goalmate.support.error.CoreApiException;
import com.goalmate.support.error.ErrorType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
	private static final String[] MENTEE_PREFIXES = {"멘티", "골메"};
	private final AppleUserProvider appleUserProvider;
	private final KakaoUserProvider kakaoUserProvider;
	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;

	private final AdminRepository adminRepository;
	private final MentorRepository mentorRepository;
	private final MenteeRepository menteeRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final MenteeService menteeService;

	@Transactional
	public LoginResult authenticateWithOauth(String identityToken, String nonce, String provider) {
		// 소셜 제공자 매핑
		SocialProvider socialProvider = SocialProvider.valueOf(provider.toUpperCase());
		OAuthMember oauthMember = getOAuthMember(identityToken, nonce, socialProvider);

		// Mentee 조회 또는 생성
		MenteeEntity menteeEntity = menteeRepository.findBySocialId(oauthMember.subject())
			.orElseGet(() -> createNewMentee(oauthMember, socialProvider));

		// 데이터베이스에 저장
		menteeEntity = menteeRepository.save(menteeEntity);
		log.info(">>>>>> 멤버 회원가입 또는 로그인 완료: {}", menteeEntity.getSocialId());

		// 토큰 생성 및 결과 반환
		TokenPair tokenPair = jwtProvider.generateTokenPair(menteeEntity.getId(), menteeEntity.getRole().name());
		return new LoginResult(tokenPair.accessToken(), tokenPair.refreshToken(), menteeEntity.isPending());
	}

	@Transactional
	public TokenPair reissue(String refreshToken) {
		jwtProvider.verifyToken(refreshToken);
		RefreshToken token = refreshTokenRepository.findById(refreshToken)
			.orElseThrow(() -> new CoreApiException(ErrorType.UNAUTHORIZED, "Invalid refresh token"));
		MenteeEntity mentee = menteeRepository.findById(token.getMenteeId())
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "Mentee not found"));
		refreshTokenRepository.deleteById(refreshToken);
		return jwtProvider.generateTokenPair(mentee.getId(), mentee.getRole().name());
	}

	public TokenPair loginAdmin(LoginAdminRequest request) {
		AdminEntity admin = adminRepository.findByLoginId(request.getId())
			.orElseThrow(() -> new CoreApiException(ErrorType.UNAUTHORIZED, "Invalid login ID"));
		validatePassword(request.getPassword(), admin.getPassword());
		return jwtProvider.generateTokenPair(admin.getId(), admin.getRole().name());
	}

	public TokenPair loginMentor(LoginMentorRequest request) {
		MentorEntity mentor = mentorRepository.findByEmail(request.getId())
			.orElseThrow(() -> new CoreApiException(ErrorType.UNAUTHORIZED, "Invalid login ID"));
		validatePassword(request.getPassword(), mentor.getPassword());
		return jwtProvider.generateTokenPair(mentor.getId(), mentor.getRole().name());
	}

	public void withdraw(CurrentUserContext user) {
		if (!user.isMentee()) {
			throw new CoreApiException(ErrorType.FORBIDDEN, "Contact the administrator if you are not a mentee");
		}
		MenteeEntity mentee = menteeService.getMenteeById(user.userId());
		if (mentee.isDeleted()) {
			throw new CoreApiException(ErrorType.FORBIDDEN, "Already withdrawn");
		}
		mentee.delete();
		// TODO: 이벤트 발행으로 변경필요
		if (mentee.getProvider().equals(SocialProvider.KAKAO)) {
			kakaoUserProvider.unlinkUser(mentee.getSocialId());
		}
	}

	// for test
	public TokenPair getTestUser() {
		MenteeEntity mentee = menteeRepository.findBySocialId("0000000000")
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "Test user not found"));
		return jwtProvider.generateTokenPair(mentee.getId(), mentee.getRole().name());
	}

	private MenteeEntity createNewMentee(OAuthMember oauthMember, SocialProvider socialProvider) {
		// 새로운 Mentee 생성
		log.info(">>>>>> 새로운 멤버 생성: {}", oauthMember.subject());
		return MenteeEntity.builder()
			.name(createMenteeName())
			.email(oauthMember.email())
			.socialId(oauthMember.subject())
			.provider(socialProvider)
			.build();
	}

	private String createMenteeName() {
		Random random = new Random();
		String prefix = MENTEE_PREFIXES[random.nextInt(MENTEE_PREFIXES.length)];
		int number = random.nextInt(900) + 100; // 100~999 범위의 숫자 생성
		return prefix + number;
	}

	private OAuthMember getOAuthMember(String identityToken, String nonce, SocialProvider socialProvider) {
		// 소셜 제공자에 따른 인증 처리
		return switch (socialProvider) {
			case APPLE -> appleUserProvider.getApplePlatformMember(identityToken, nonce);
			case KAKAO -> kakaoUserProvider.getKaKaoPlatformMember(identityToken, nonce);
			default -> throw new IllegalArgumentException("Unsupported social provider: " + socialProvider);
		};
	}

	private void validatePassword(String rawPassword, String encodedPassword) {
		if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
			throw new CoreApiException(ErrorType.UNAUTHORIZED, "Invalid password");
		}
	}
}
