package com.goalmate.infra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.goalmate.infra.feign.config.KakaoFeignConfig;
import com.goalmate.security.oauth.oidc.OIDCPublicKeys;

@FeignClient(value = "kakaoClient", url = "https://kauth.kakao.com", configuration = KakaoFeignConfig.class)
@Component
public interface KakaoAuthClient {
	@GetMapping(value = "/.well-known/jwks.json")
	OIDCPublicKeys getPublicKeys();

}
