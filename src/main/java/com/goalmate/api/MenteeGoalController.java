package com.goalmate.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.model.MenteeGoalSummaryPagingResponse;
import com.goalmate.security.SecurityUtil;
import com.goalmate.service.MenteeGoalService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MenteeGoalController implements MenteeGoalApi {
	private final MenteeGoalService menteeGoalService;

	@Override
	public ResponseEntity getMenteeGoals(Integer page, Integer size) {
		Long menteeId = SecurityUtil.getCurrentUserId();
		MenteeGoalSummaryPagingResponse response = menteeGoalService.getMenteeGoals(menteeId, page, size);
		return ResponseEntity.ok(response);
	}
}
