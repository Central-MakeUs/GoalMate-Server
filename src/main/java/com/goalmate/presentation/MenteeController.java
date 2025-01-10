package com.goalmate.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.MenteeApi;
import com.goalmate.api.model.ModelApiResponse;
import com.goalmate.api.model.ValidateMenteeName200Response;

@RestController
public class MenteeController implements MenteeApi {
	@Override
	public ResponseEntity<Void> getMenteeInfo() throws Exception {
		return MenteeApi.super.getMenteeInfo();
	}

	@Override
	public ResponseEntity<ModelApiResponse> updateMenteeName(String name) throws Exception {
		return MenteeApi.super.updateMenteeName(name);
	}

	@Override
	public ResponseEntity<ValidateMenteeName200Response> validateMenteeName(String name) throws Exception {
		return MenteeApi.super.validateMenteeName(name);
	}
}
