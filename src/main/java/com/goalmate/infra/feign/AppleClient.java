package com.goalmate.infra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.goalmate.infra.feign.config.AppleFeignConfig;
import com.goalmate.security.oauth.oidc.OIDCPublicKeys;

@FeignClient(name = "apple-public-key-client", url = "https://appleid.apple.com/auth", configuration = AppleFeignConfig.class)
@Component
public interface AppleClient {
	@GetMapping("/keys")
	OIDCPublicKeys getApplePublicKeys();
}
