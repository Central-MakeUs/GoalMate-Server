package com.goalmate.api;

import org.springframework.http.ResponseEntity;

import com.goalmate.api.model.AddComment200Response;
import com.goalmate.api.model.CommentRequest;
import com.goalmate.api.model.GetComments200Response;
import com.goalmate.api.model.HasNewComments200Response;

public class CommentController implements CommentApi {
	@Override
	public ResponseEntity<GetComments200Response> getComments(Integer menteeGoalId) throws Exception {
		return CommentApi.super.getComments(menteeGoalId);
	}

	@Override
	public ResponseEntity<GetComments200Response> getFinalComment(Integer menteeGoalId) throws Exception {
		return CommentApi.super.getFinalComment(menteeGoalId);
	}

	@Override
	public ResponseEntity<AddComment200Response> addComment(Integer menteeGoalId, CommentRequest commentRequest) throws
		Exception {
		return CommentApi.super.addComment(menteeGoalId, commentRequest);
	}

	@Override
	public ResponseEntity<AddComment200Response> updateComment(Integer commentId, CommentRequest commentRequest) throws
		Exception {
		return CommentApi.super.updateComment(commentId, commentRequest);
	}

	@Override
	public ResponseEntity<HasNewComments200Response> hasNewComments() throws Exception {
		return CommentApi.super.hasNewComments();
	}

	@Override
	public ResponseEntity<Void> deleteComment(Integer commentId) throws Exception {
		return CommentApi.super.deleteComment(commentId);
	}
}
