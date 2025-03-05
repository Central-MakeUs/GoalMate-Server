package com.goalmate.api.v2;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.v2.dto.request.PageCondition;
import com.goalmate.api.v2.dto.response.GoalSummaryResponse;
import com.goalmate.api.v2.dto.response.PageResponse;
import com.goalmate.api.v2.dto.response.ParticipationResponse;
import com.goalmate.api.v2.service.GoalServiceV2;
import com.goalmate.api.v2.service.ParticipationService;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/v2/goals")
@RestController
public class GoalControllerV2 {
	private final GoalServiceV2 goalService;
	private final ParticipationService participationService;

	// 전체 목표 조회 (유저, 관리자)
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<GoalSummaryResponse>>> getAllGoals(
		@ModelAttribute PageCondition pageCondition) {
		return null;
	}

	// GET /goals/created   // 본인이 만든 목표 조회 (멘토만)
	@GetMapping("/created")
	public ResponseEntity<ApiResponse<PageResponse<GoalSummaryResponse>>> getMyCreatedGoals(
		@AuthenticationPrincipal Long mentorId,
		@ModelAttribute PageCondition pageCondition) {
		return null;
	}

	// GET /goals/{goalId}/participations   // 목표의 참여 조회 (멘토, 관리자)
	@GetMapping("/{goalId}/participations")
	public ResponseEntity<ApiResponse<PageResponse<ParticipationResponse>>> getParticipationsByGoal(
		@PathVariable Long goalId,
		@ModelAttribute PageCondition pageCondition) {
		PageResponse<ParticipationResponse> response = participationService.getParticipationsByGoal(
			goalId,
			pageCondition);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

}
