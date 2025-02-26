package com.goalmate.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.goalmate.api.v2.dto.request.PageCondition;
import com.goalmate.api.v2.dto.response.CommentRoomResponse;
import com.goalmate.api.v2.dto.response.PageResponse;
import com.goalmate.api.v2.dto.response.ParticipationResponse;
import com.goalmate.domain.mentee.Role;
import com.goalmate.repository.CommentRepository;
import com.goalmate.repository.CommentRoomRepository;
import com.goalmate.repository.MenteeGoalRepository;
import com.goalmate.util.PageRequestUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AdminService {
	private final MenteeGoalService menteeGoalService;
	private final MenteeGoalRepository menteeGoalRepository;
	private final CommentRoomRepository commentRoomRepository;
	private final CommentRepository commentRepository;

	public PageResponse<ParticipationResponse> getParticipationsByGoal(Long goalId, PageCondition pageCondition) {
		final Pageable pageable = PageRequestUtil.createPageRequest(pageCondition.getPage(), pageCondition.getSize());
		final List<ParticipationResponse> participations = menteeGoalRepository.findByGoalId(goalId, pageable)
			.stream()
			.map(participation ->
				ParticipationResponse.of(
					participation,
					menteeGoalService.getTodoProgress(participation)))
			.toList();
		return PageResponse.of(
			PageableExecutionUtils.getPage(
				participations,
				pageable,
				() -> menteeGoalRepository.countByGoalId(goalId))
		);
	}

	public PageResponse<CommentRoomResponse> getCommentRoomsByParticipation(
		Long participationId,
		PageCondition pageCondition
	) {
		final Pageable pageable = PageRequestUtil.createPageRequest(pageCondition.getPage(), pageCondition.getSize());
		final List<CommentRoomResponse> commentRooms = commentRoomRepository.findByParticipationId(participationId,
				pageable)
			.stream()
			.map(commentRoom -> {
				Long countedUnreadComments = commentRepository.countUnreadComments(
					commentRoom.getId(),
					Role.ROLE_MENTOR
				);
				return CommentRoomResponse.of(commentRoom, countedUnreadComments);
			})
			.toList();
		return PageResponse.of(
			PageableExecutionUtils.getPage(
				commentRooms,
				pageable,
				() -> commentRoomRepository.countByParticipationId(participationId))
		);
	}

}
