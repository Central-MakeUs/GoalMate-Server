package com.goalmate.util;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalmate.support.error.CoreApiException;
import com.goalmate.support.error.ErrorType;
import com.goalmate.support.response.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class HttpResponseUtil {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static void writeSuccessResponse(HttpServletResponse response, HttpStatus httpStatus, Object body)
		throws IOException {
		String responseBody = OBJECT_MAPPER.writeValueAsString(ApiResponse.success(body));
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(httpStatus.value());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseBody);
	}

	public static void writeErrorResponse(HttpServletResponse response, CoreApiException e) throws
		IOException {
		final ErrorType errorType = e.getErrorType();
		final Object errorData = e.getData();
		String responseBody = OBJECT_MAPPER.writeValueAsString(ApiResponse.error(errorType, errorData));
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(errorType.getStatus().value());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseBody);
	}
}
