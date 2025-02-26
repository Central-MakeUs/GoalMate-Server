package com.goalmate.api.v2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.v2.dto.request.PageCondition;
import com.goalmate.api.v2.dto.response.CommentRoomResponse;
import com.goalmate.api.v2.dto.response.PageResponse;
import com.goalmate.api.v2.dto.response.ParticipationResponse;
import com.goalmate.service.AdminService;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v2/admin")
@RestController
public class AdminController {
	private final AdminService adminService;

	@GetMapping("/goals/{goalId}/participations")
	public ResponseEntity<ApiResponse<PageResponse<ParticipationResponse>>> getParticipationsByGoal(
		@PathVariable Long goalId,
		@ModelAttribute PageCondition pageCondition) {
		PageResponse<ParticipationResponse> response = adminService.getParticipationsByGoal(goalId, pageCondition);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/participations/{participationId}/comment-rooms")
	public ResponseEntity<ApiResponse<PageResponse<CommentRoomResponse>>> getCommentRoomsByParticipation(
		@PathVariable Long participationId,
		@ModelAttribute PageCondition pageCondition) {
		PageResponse<CommentRoomResponse> response = adminService.getCommentRoomsByParticipation(
			participationId,
			pageCondition
		);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
