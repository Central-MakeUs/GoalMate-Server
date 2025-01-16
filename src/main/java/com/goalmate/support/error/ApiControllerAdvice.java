package com.goalmate.support.error;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.goalmate.support.response.ApiResponse;

@RestControllerAdvice
public class ApiControllerAdvice {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
		log.error("Exception: {} ", e.getMessage(), e);
		return new ResponseEntity<>(
			ApiResponse.error(ErrorType.INTERNAL_ERROR, e.getMessage()),
			ErrorType.INTERNAL_ERROR.getStatus());
	}

	@ExceptionHandler(CoreApiException.class)
	public ResponseEntity<ApiResponse<?>> handleCoreApiException(CoreApiException e) {
		switch (e.getErrorType().getLogLevel()) {
			case ERROR -> log.error("CoreApiException : {}", e.getMessage(), e);
			case WARN -> log.warn("CoreApiException : {}", e.getMessage(), e);
			default -> log.info("CoreApiException : {}", e.getMessage(), e);
		}
		return new ResponseEntity<>(ApiResponse.error(e.getErrorType(), e.getData()), e.getErrorType().getStatus());
	}

	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.error(">>>>> HttpRequest Parsing Error : ", e);
		ErrorType errorType = ErrorType.BAD_REQUEST;
		return ResponseEntity.status(errorType.getStatus()).body(ApiResponse.error(errorType, e.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		// 실패한 validation 을 담을 Map
		Map<String, String> failedValidations = new HashMap<>();
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		// fieldErrors 를 순회하며 failedValidations 에 담는다.
		fieldErrors.forEach(error -> failedValidations.put(error.getField(), error.getDefaultMessage()));
		ApiResponse<?> errorResponse = ApiResponse.error(ErrorType.BAD_REQUEST, failedValidations);
		return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
	}
}
