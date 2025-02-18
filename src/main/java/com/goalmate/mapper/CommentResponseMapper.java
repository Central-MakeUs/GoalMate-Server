package com.goalmate.mapper;

import java.time.ZoneOffset;
import java.util.List;

import com.goalmate.api.model.CommentPagingResponse;
import com.goalmate.api.model.CommentResponse;
import com.goalmate.api.model.CommentRoomPagingResponse;
import com.goalmate.api.model.CommentRoomResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.domain.comment.CommentEntity;
import com.goalmate.domain.comment.CommentRoomEntity;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;

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

	public static CommentRoomResponse mapToCommentRoomResponse(
		CommentRoomEntity commentRoom,
		long countedUnreadComments
	) {
		MenteeGoalEntity menteeGoal = commentRoom.getMenteeGoal();
		GoalEntity goal = menteeGoal.getGoalEntity();

		CommentRoomResponse response = new CommentRoomResponse();
		response.setCommentRoomId(commentRoom.getId());
		response.setMenteeGoalId(menteeGoal.getId());
		response.setMenteeGoalTitle(goal.getTitle());
		response.setMentorName(commentRoom.getMentor().getName());
		response.setStartDate(menteeGoal.getStartDate());
		response.setEndDate(menteeGoal.getEndDate());
		response.setNewCommentsCount(countedUnreadComments);

		return response;
	}

	public static CommentRoomPagingResponse mapToCommentRoomPagingResponse(
		List<CommentRoomResponse> commentRoomResponses,
		PageResponse pageResponse
	) {
		CommentRoomPagingResponse response = new CommentRoomPagingResponse();
		response.setCommentRooms(commentRoomResponses);
		response.setPage(pageResponse);
		return response;
	}

}
