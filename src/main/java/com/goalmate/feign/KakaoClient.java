package com.goalmate.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.goalmate.feign.config.KakaoFeignConfig;
import com.goalmate.oauth.oidc.OIDCPublicKeys;

@FeignClient(value = "kakaoClient", url = "https://kauth.kakao.com", configuration = KakaoFeignConfig.class)
@Component
public interface KakaoClient {
	@GetMapping(value = "/.well-known/jwks.json")
	OIDCPublicKeys getPublicKeys();
}
