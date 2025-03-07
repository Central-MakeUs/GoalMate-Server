package com.goalmate.infra.feign.exception;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignClientExceptionErrorDecoder implements ErrorDecoder {

	Logger logger = LoggerFactory.getLogger(FeignClientExceptionErrorDecoder.class);

	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.status() >= 400 && response.status() <= 499) {
			try {
				logger.error("{}번 에러 발생 : {}", response.status(),
					new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8));
			} catch (IOException e) {
				logger.error("Error decoding Feign client response", e);
			}
			return new RuntimeException("400");
		} else {
			logger.error("500번대 에러 발생 : {}", response.reason());
			return new RuntimeException("500");
		}
	}
}
