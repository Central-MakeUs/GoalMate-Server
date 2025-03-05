package com.goalmate.api.v2.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.goalmate.api.v2.dto.request.PageCondition;
import com.goalmate.api.v2.dto.response.CommentRoomResponse;
import com.goalmate.api.v2.dto.response.PageResponse;
import com.goalmate.repository.CommentRepository;
import com.goalmate.repository.CommentRoomRepository;
import com.goalmate.util.PageRequestUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentRoomService {
	private final CommentRoomRepository commentRoomRepository;
	private final CommentRepository commentRepository;

	public PageResponse<CommentRoomResponse> getCommentRoomsByParticipation(
		Long participationId,
		PageCondition pageCondition
	) {
		final Pageable pageable = PageRequestUtil.createPageRequest(pageCondition.getPage(), pageCondition.getSize());
		final List<CommentRoomResponse> commentRooms = commentRoomRepository.findByParticipationId(participationId,
				pageable)
			.stream()
			.map(commentRoom -> {
				int countedUnreadComments = commentRepository.countUnreadCommentsByRoomAndReceiver(
					commentRoom.getId(),
					commentRoom.getMentor().getId(), // 지금은 멘토 전용
					commentRoom.getMentor().getRole()
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
