package com.goalmate.api;

import org.springframework.http.ResponseEntity;

import com.goalmate.api.model.AddMenteeComment200Response;
import com.goalmate.api.model.CommentPagingResponse;
import com.goalmate.api.model.CommentRequest;
import com.goalmate.api.model.CommentResponse;
import com.goalmate.api.model.GetFinalComment200Response;
import com.goalmate.api.model.HasNewComments200Response;
import com.goalmate.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentController implements CommentApi {
	private final CommentService commentService;

	@Override
	public ResponseEntity addMenteeComment(Long menteeGoalId, CommentRequest commentRequest) {
		CommentResponse response = commentService.addMenteeComment(menteeGoalId, commentRequest.getComment());
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity getComments(Long menteeGoalId, Integer page, Integer size) {
		CommentPagingResponse response = commentService.getComments(menteeGoalId, page, size);
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<GetFinalComment200Response> getFinalComment(Long menteeGoalId) throws Exception {
		return CommentApi.super.getFinalComment(menteeGoalId);
	}

	@Override
	public ResponseEntity<AddMenteeComment200Response> updateComment(Long commentId,
		CommentRequest commentRequest) throws Exception {
		return CommentApi.super.updateComment(commentId, commentRequest);
	}

	@Override
	public ResponseEntity<HasNewComments200Response> hasNewComments() throws Exception {
		return CommentApi.super.hasNewComments();
	}

	@Override
	public ResponseEntity<Void> deleteComment(Long commentId) {
		commentService.deleteComment(commentId);
		return ResponseEntity.ok(null);
	}
}
