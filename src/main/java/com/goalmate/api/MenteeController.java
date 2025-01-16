package com.goalmate.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.service.MenteeService;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MenteeController implements MenteeApi {
	private final MenteeService menteeService;

	@Override
	public ResponseEntity<Void> getMenteeInfo() throws Exception {
		return MenteeApi.super.getMenteeInfo();
	}

	@Override
	public ResponseEntity updateMenteeName(String name) throws Exception {
		menteeService.updateMenteeName(name);
		return ResponseEntity.ok(ApiResponse.success());
	}

	@Override
	public ResponseEntity validateMenteeName(String name) throws Exception {
		menteeService.validateMenteeName(name);
		return ResponseEntity.ok(ApiResponse.success());
	}
}
