package com.goalmate.infra.feign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import com.goalmate.GoalmateApplication;

@Configuration
@EnableFeignClients(basePackageClasses = GoalmateApplication.class)
public class FeignClientConfig {
} 
 
