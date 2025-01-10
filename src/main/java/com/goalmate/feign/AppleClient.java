package com.goalmate.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.goalmate.feign.config.AppleSocialFeignConfig;
import com.goalmate.oauth.apple.ApplePublicKeys;

@FeignClient(name = "apple-public-key-client", url = "https://appleid.apple.com/auth", configuration = AppleSocialFeignConfig.class)
@Component
public interface AppleClient {
	@GetMapping("/keys")
	ApplePublicKeys getApplePublicKeys();

}
