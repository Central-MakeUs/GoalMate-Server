package com.goalmate.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.api.model.CommentPagingResponse;
import com.goalmate.api.model.CommentResponse;
import com.goalmate.api.model.CommentRoomPagingResponse;
import com.goalmate.api.model.CommentRoomResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.domain.comment.CommentEntity;
import com.goalmate.domain.comment.CommentRoomEntity;
import com.goalmate.domain.comment.CommentType;
import com.goalmate.domain.mentee.Role;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.mapper.CommentResponseMapper;
import com.goalmate.mapper.PageResponseMapper;
import com.goalmate.repository.CommentRepository;
import com.goalmate.repository.CommentRoomRepository;
import com.goalmate.security.util.CurrentUserContext;
import com.goalmate.security.util.SecurityUtil;
import com.goalmate.support.error.CoreApiException;
import com.goalmate.support.error.ErrorType;
import com.goalmate.util.PageRequestUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {
	private final MenteeGoalService menteeGoalService;
	private final CommentRoomRepository commentRoomRepository;
	private final CommentRepository commentRepository;

	public CommentResponse addMenteeComment(Long roomId, String comment) {
		CommentRoomEntity commentRoom = getCommentRoom(roomId);
		MenteeGoalEntity menteeGoal = commentRoom.getMenteeGoal();

		LocalDate today = LocalDate.now();
		LocalDateTime startOfDay = today.atStartOfDay(); // 오늘 00:00:00
		LocalDateTime endOfDay = today.atTime(LocalTime.MAX); // 오늘 23:59:59

		// 오늘 코멘트가 이미 작성되었는지 확인
		commentRepository.findTodayCommentFromMentee(roomId, startOfDay, endOfDay)
			.ifPresent(baseComment -> {
				log.warn("Comment Already Exists: {}", baseComment);
				throw new CoreApiException(ErrorType.CONFLICT, "Today's Comment Already Exists");
			});

		CommentEntity commentEntity = CommentEntity.builder()
			.comment(comment)
			.writerId(menteeGoal.getMenteeEntity().getId())
			.writerName(menteeGoal.getMenteeEntity().getName())
			.writerRole(Role.ROLE_MENTEE)
			.commentType(CommentType.DAILY)
			.commentRoom(commentRoom)
			.build();
		commentRepository.save(commentEntity);

		return CommentResponseMapper.mapToCommentResponse(commentEntity);
	}

	public CommentPagingResponse getComments(Long roomId, Integer page, Integer size) {
		CommentRoomEntity commentRoom = getCommentRoom(roomId);

		Pageable pageable = PageRequestUtil.createPageRequest(page, size);
		Page<CommentEntity> comments = commentRepository.findLatestCommentsByRoomId(commentRoom.getId(), pageable);

		List<CommentResponse> commentResponses = comments.stream()
			.map(CommentResponseMapper::mapToCommentResponse)
			.toList();
		PageResponse pageResponse = PageResponseMapper.mapToPageResponse(comments);

		return CommentResponseMapper.mapToCommentPagingResponse(commentResponses, pageResponse);
	}

	public CommentRoomPagingResponse getCommentRooms(CurrentUserContext userContext, Integer page, Integer size) {
		Page<CommentRoomEntity> commentRooms = null;
		Pageable pageable = PageRequestUtil.createPageRequest(page, size);

		if (userContext.userRole().equals(Role.ROLE_MENTEE))
			commentRooms = commentRoomRepository.findByMenteeIdOrderByUpdatedAtDesc(userContext.userId(), pageable);
		else if (userContext.userRole().equals(Role.ROLE_MENTOR))
			commentRooms = commentRoomRepository.findByMentorIdOrderByUpdatedAtDesc(userContext.userId(), pageable);

		List<CommentRoomResponse> commentRoomResponses = commentRooms.stream()
			.map(commentRoom -> {
				Long countedUnreadComments = commentRepository.countUnreadComments(commentRoom.getId(),
					Role.ROLE_MENTOR);
				return CommentResponseMapper.mapToCommentRoomResponse(commentRoom, countedUnreadComments);
			})
			.toList();
		PageResponse pageResponse = PageResponseMapper.mapToPageResponse(commentRooms);
		return CommentResponseMapper.mapToCommentRoomPagingResponse(commentRoomResponses, pageResponse);
	}

	// public boolean countNewComments(Long commentRoomId) {
	// 	// notRead 이면서,
	// 	// TODO 코멘트 룸API 뚫기, 코멘트 API 마무리하기
	// 	return !commentRepository.findNotReadMentorComment(menteeGoalId).isEmpty();
	// }

	public void deleteComment(Long commentId) {
		CommentEntity comment = getComment(commentId);
		Long currentUserId = SecurityUtil.getCurrentUserId();
		Role currentUserRole = SecurityUtil.getCurrenUserRole();
		if (!comment.isWriter(currentUserId, currentUserRole)) {
			throw new CoreApiException(ErrorType.FORBIDDEN, "Comment writer is not correct");
		}
		commentRepository.delete(comment);
	}

	private CommentEntity getComment(Long commentId) {
		return commentRepository.findById(commentId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "Comment not found"));
	}

	private CommentRoomEntity getCommentRoom(Long roomId) {
		return commentRoomRepository.findById(roomId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "Comment Room not found"));
	}
}
