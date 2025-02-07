package com.goalmate.mapper;

import java.time.ZoneOffset;
import java.util.List;

import com.goalmate.api.model.CommentPagingResponse;
import com.goalmate.api.model.CommentResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.domain.comment.CommentEntity;

public class CommentResponseMapper {
	public static CommentResponse mapToCommentResponse(CommentEntity comment) {
		CommentResponse response = new CommentResponse();
		response.setId(comment.getId());
		response.setComment(comment.getComment());
		response.setCommentedAt(comment.getCreatedAt().atOffset(ZoneOffset.UTC));
		response.setWriter(comment.getWriterName());
		response.setWriterRole(comment.getWriterRole().getValue());
		return response;
	}

	public static CommentPagingResponse mapToCommentPagingResponse(
		List<CommentResponse> commentResponses,
		PageResponse pageResponse) {
		CommentPagingResponse response = new CommentPagingResponse();
		response.setComments(commentResponses);
		response.setPage(pageResponse);
		return response;
	}
}
