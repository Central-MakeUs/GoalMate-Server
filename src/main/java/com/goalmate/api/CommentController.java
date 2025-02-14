package com.goalmate.api;

import org.springframework.http.ResponseEntity;

import com.goalmate.api.model.CommentPagingResponse;
import com.goalmate.api.model.CommentRequest;
import com.goalmate.api.model.CommentResponse;
import com.goalmate.api.model.CommentRoomPagingResponse;
import com.goalmate.security.util.CurrentUserContext;
import com.goalmate.security.util.SecurityUtil;
import com.goalmate.service.CommentService;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentController implements CommentApi {
	private final CommentService commentService;

	@Override
	public ResponseEntity getCommentRooms(Integer page, Integer size) {
		CurrentUserContext userContext = SecurityUtil.getCurrentUser();
		CommentRoomPagingResponse response = commentService.getCommentRooms(userContext, page, size);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity addCommentFromMentee(Long roomId, CommentRequest commentRequest) {
		CommentResponse response = commentService.addMenteeComment(roomId, commentRequest.getComment());
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity getComments(Long roomId, Integer page, Integer size) {
		CommentPagingResponse response = commentService.getComments(roomId, page, size);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity updateCommentFromMentee(Long commentId, CommentRequest commentRequest) throws Exception {
		return CommentApi.super.updateCommentFromMentee(commentId, commentRequest);
	}

	@Override
	public ResponseEntity deleteCommentFromMentee(Long commentId) {
		commentService.deleteComment(commentId);
		return ResponseEntity.ok(ApiResponse.success());
	}
}
