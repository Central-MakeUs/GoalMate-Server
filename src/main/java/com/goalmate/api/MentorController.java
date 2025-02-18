package com.goalmate.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.model.MentorLetterRequest;
import com.goalmate.api.model.UpdateMentorLetter200Response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MentorController implements MentorApi {

	@Override
	public ResponseEntity<UpdateMentorLetter200Response> updateMentorLetter(Long menteeGoalId,
		MentorLetterRequest mentorLetterRequest) throws Exception {
		return MentorApi.super.updateMentorLetter(menteeGoalId, mentorLetterRequest);
	}
}
