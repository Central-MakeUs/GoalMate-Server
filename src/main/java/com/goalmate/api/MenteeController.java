package com.goalmate.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.model.MenteeInfoResponse;
import com.goalmate.api.model.NameAvailabilityResponse;
import com.goalmate.security.user.SecurityUtil;
import com.goalmate.service.MenteeService;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MenteeController implements MenteeApi {
	private final MenteeService menteeService;

	@Override
	public ResponseEntity getMenteeInfo() {
		final Long userId = SecurityUtil.getCurrentUserId();
		MenteeInfoResponse response = menteeService.getMenteeInfo(userId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity updateMenteeName(String name) {
		final Long userId = SecurityUtil.getCurrentUserId();
		String validatedName = menteeService.updateMenteeName(userId, name);
		return ResponseEntity.ok(ApiResponse.success(validatedName));
	}

	@Override
	public ResponseEntity checkNameAvailability(String name) {
		NameAvailabilityResponse response = new NameAvailabilityResponse();
		boolean availability = menteeService.isNameAvailable(name);
		response.setIsAvailable(availability);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

}
