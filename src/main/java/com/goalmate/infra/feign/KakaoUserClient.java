package com.goalmate.infra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.goalmate.infra.feign.config.KakaoFeignConfig;

@FeignClient(value = "kakaoUserClient", url = "https://kapi.kakao.com", configuration = KakaoFeignConfig.class)
@Component
public interface KakaoUserClient {

	@PostMapping(value = "/v1/user/unlink", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	ResponseEntity<String> unlinkUser(
		@RequestHeader("Authorization") String adminKey,
		@RequestParam("target_id_type") String targetIdType,
		@RequestParam("target_id") Long targetId
	);
}
