package com.goalmate.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.MenteeApi;
import com.goalmate.api.model.CheckMenteeNameDuplicated200Response;
import com.goalmate.api.model.ModelApiResponse;

@RestController
public class MenteeController implements MenteeApi {

	@Override
	public ResponseEntity<CheckMenteeNameDuplicated200Response> checkMenteeNameDuplicated(String name) throws
		Exception {
		return MenteeApi.super.checkMenteeNameDuplicated(name);
	}

	@Override
	public ResponseEntity<Void> getMenteeInfo() throws Exception {
		return MenteeApi.super.getMenteeInfo();
	}

	@Override
	public ResponseEntity<ModelApiResponse> updateMenteeName(String name) throws Exception {
		return MenteeApi.super.updateMenteeName(name);
	}
}
