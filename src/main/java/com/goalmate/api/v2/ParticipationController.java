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
import com.goalmate.api.v2.service.CommentRoomService;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/v2/participations")
@RestController
public class ParticipationController {
	private final CommentRoomService commentRoomService;

	// GET /participants          // 전체 참여 목록 (관리자만)
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<ParticipationResponse>>> getAllParticipations() {
		return null;
	}

	// GET /participants/me       // 현재 로그인한 사용자의 참여 목록 (유저, 멘토, 관리자 공통)
	@GetMapping("/me")
	public ResponseEntity<ApiResponse<PageResponse<ParticipationResponse>>> getMyParticipations(
		@ModelAttribute PageCondition pageCondition
	) {
		return null;
	}

	@GetMapping("/{participationId}")
	public ResponseEntity<ApiResponse<ParticipationResponse>> getParticipation(@PathVariable Long participationId) {
		return null;
	}

	@GetMapping("/{participationId}/todos")
	public ResponseEntity<ApiResponse<PageResponse<ParticipationResponse>>> getTodosByParticipation(
		@PathVariable Long participationId,
		@ModelAttribute PageCondition pageCondition
	) {
		return null;
	}

	@GetMapping("/{participationId}/comment-rooms")
	public ResponseEntity<ApiResponse<PageResponse<CommentRoomResponse>>> getCommentRoomsByParticipation(
		@PathVariable Long participationId,
		@ModelAttribute PageCondition pageCondition) {
		PageResponse<CommentRoomResponse> response = commentRoomService.getCommentRoomsByParticipation(
			participationId,
			pageCondition
		);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
