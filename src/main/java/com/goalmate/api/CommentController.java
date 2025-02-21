package com.goalmate.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.model.CommentPagingResponse;
import com.goalmate.api.model.CommentRequest;
import com.goalmate.api.model.CommentResponse;
import com.goalmate.api.model.CommentRoomPagingResponse;
import com.goalmate.security.user.CurrentUserContext;
import com.goalmate.security.user.SecurityUtil;
import com.goalmate.service.CommentService;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController implements CommentApi {
	private final CommentService commentService;

	@Override
	public ResponseEntity getCommentRooms(Integer page, Integer size) {
		CurrentUserContext user = SecurityUtil.getCurrentUser();
		CommentRoomPagingResponse response = commentService.getCommentRooms(user, page, size);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity addComment(Long roomId, CommentRequest commentRequest) {
		CurrentUserContext user = SecurityUtil.getCurrentUser();
		CommentResponse response = commentService.addComment(user, roomId, commentRequest.getComment());
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity getComments(Long roomId, Integer page, Integer size) {
		CurrentUserContext user = SecurityUtil.getCurrentUser();
		CommentPagingResponse response = commentService.getComments(user, roomId, page, size);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity updateComment(Long commentId, CommentRequest commentRequest) {
		CurrentUserContext user = SecurityUtil.getCurrentUser();
		CommentResponse response = commentService.updateComment(user, commentId, commentRequest.getComment());
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity deleteComment(Long commentId) {
		CurrentUserContext user = SecurityUtil.getCurrentUser();
		commentService.deleteComment(user, commentId);
		return ResponseEntity.ok(ApiResponse.success());
	}

}
