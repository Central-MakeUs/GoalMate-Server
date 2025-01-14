package com.goalmate.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.MenteeApi;
import com.goalmate.api.model.ModelApiResponse;
import com.goalmate.service.MenteeService;

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
	public ResponseEntity<ModelApiResponse> updateMenteeName(String name) throws Exception {
		// TODO 구현
		return MenteeApi.super.updateMenteeName(name);
	}

	@Override
	public ResponseEntity validateMenteeName(String name) throws Exception {
		// TODO 구현
		return MenteeApi.super.validateMenteeName(name);
	}
}
