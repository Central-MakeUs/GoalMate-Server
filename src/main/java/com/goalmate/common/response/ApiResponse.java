package com.goalmate.common.response;

import com.goalmate.common.error.ErrorType;

public class ApiResponse<S> {
	private final ResponseStatus status;

	private final String code;

	private final String message;

	private final S data;

	public ApiResponse(ResponseStatus status, String code, String message, S data) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static ApiResponse<?> success() {
		return ApiResponse.success(null);
	}

	public static <S> ApiResponse<S> success(S data) {
		return new ApiResponse<>(ResponseStatus.SUCCESS, "200", "정상적으로 처리되었습니다.", data);
	}

	public static ApiResponse<?> error(ErrorType error) {
		return ApiResponse.error(error, null);
	}

	public static <S> ApiResponse<S> error(ErrorType error, S errorData) {
		return new ApiResponse<>(ResponseStatus.ERROR, error.getCode().toString(), error.getMessage(), errorData);

	}

	public ResponseStatus getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}
}
