package com.goalmate.security.jwt.filter;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.goalmate.support.HttpResponseUtil;
import com.goalmate.support.error.CoreApiException;
import com.goalmate.support.error.ErrorType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException {
		HttpResponseUtil.writeErrorResponse(
			response,
			new CoreApiException(ErrorType.FORBIDDEN, "[Error] 접근 권한이 없습니다."));
	}
}
