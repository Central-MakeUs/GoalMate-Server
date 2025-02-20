package com.goalmate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTest {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testPasswordEncoding() {
		String rawPassword = "rawPassword"; // 원문 패스워드
		String encodedPassword = passwordEncoder.encode(rawPassword);

		// 인코딩 결과 출력 (DB에 등록할 때 사용)
		System.out.println("Encoded password: " + encodedPassword);

		// 인코딩된 패스워드와 원문이 일치하는지 검증
		Assertions.assertTrue(
			passwordEncoder.matches(rawPassword, encodedPassword));
	}
}
