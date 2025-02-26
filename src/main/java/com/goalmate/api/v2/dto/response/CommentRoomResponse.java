package com.goalmate.api.v2.dto.response;

import java.time.LocalDate;

import com.goalmate.domain.comment.CommentRoomEntity;

public record CommentRoomResponse(
	Long commentRoomId,
	Long menteeGoalId,
	String title,
	LocalDate startDate,
	LocalDate endDate,
	String menteeName,
	String mentorName,
	String profileImageUrl,
	Long newCommentsCount
) {
	public static CommentRoomResponse of(
		CommentRoomEntity commentRoom,
		Long newCommentsCount
	) {
		return new CommentRoomResponse(
			commentRoom.getId(),
			commentRoom.getMenteeGoal().getId(),
			commentRoom.getMenteeGoal().getGoal().getTitle(),  // TODO: CommentRoom에 Title을 추가하고 그걸로 대체
			commentRoom.getMenteeGoal().getStartDate(),
			commentRoom.getMenteeGoal().getEndDate(),
			commentRoom.getMenteeGoal().getMentee().getName(),
			commentRoom.getMentor().getName(),
			commentRoom.getMentor().getProfileImageUrl(), // TODO: CommentRoom에 ProfileImage를 추가하고 그걸로 대체
			newCommentsCount
		);
	}
}
