package com.goalmate.infra.feign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import com.goalmate.GoalmateApplication;

@Configuration
@EnableFeignClients(basePackages = "com.goalmate.infra.feign", basePackageClasses = GoalmateApplication.class)
public class FeignClientConfig {
}
