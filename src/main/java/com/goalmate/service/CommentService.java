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
import com.goalmate.domain.mentee.Role;
import com.goalmate.mapper.CommentResponseMapper;
import com.goalmate.mapper.PageResponseMapper;
import com.goalmate.repository.CommentRepository;
import com.goalmate.repository.CommentRoomRepository;
import com.goalmate.security.user.CurrentUserContext;
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

	public CommentRoomPagingResponse getCommentRooms(CurrentUserContext user, Integer page, Integer size) {
		Page<CommentRoomEntity> commentRooms = null;
		Pageable pageable = PageRequestUtil.createPageRequest(page, size);

		commentRooms = getCommentRoomsByRole(user, commentRooms, pageable);

		List<CommentRoomResponse> commentRoomResponses = commentRooms.stream()
			.map(commentRoom -> {
				Long countedUnreadComments = commentRepository
					.countUnreadComments(commentRoom.getId(), user.userRole());
				return CommentResponseMapper.mapToCommentRoomResponse(commentRoom, countedUnreadComments);
			})
			.toList();
		PageResponse pageResponse = PageResponseMapper.mapToPageResponse(commentRooms);
		return CommentResponseMapper.mapToCommentRoomPagingResponse(commentRoomResponses, pageResponse);
	}

	public CommentResponse addComment(CurrentUserContext user, Long roomId, String comment) {
		String writerName = null;
		CommentRoomEntity commentRoom = getCommentRoom(roomId);

		if (user.isMentee()) {
			validateUser(user, commentRoom.getMentee().getId());

			LocalDate today = LocalDate.now();
			LocalDateTime startOfDay = today.atStartOfDay(); // 오늘 00:00:00
			LocalDateTime endOfDay = today.atTime(LocalTime.MAX); // 오늘 23:59:59

			// 오늘 코멘트가 이미 작성되었는지 확인
			commentRepository.findTodayCommentFromMentee(roomId, startOfDay, endOfDay)
				.ifPresent(baseComment -> {
					log.warn("Comment Already Exists: {}", baseComment);
					throw new CoreApiException(ErrorType.CONFLICT, "Today's Comment Already Exists");
				});
			writerName = commentRoom.getMentee().getName();
		} else if (user.isMentor()) {
			validateUser(user, commentRoom.getMentor().getId());
			writerName = commentRoom.getMentor().getName();
		} else {
			throw new CoreApiException(ErrorType.FORBIDDEN, "User Role is not correct");
		}

		CommentEntity commentEntity = CommentEntity.builder()
			.comment(comment)
			.writerId(user.userId())
			.writerName(writerName)
			.writerRole(user.userRole())
			.commentRoom(commentRoom)
			.build();
		commentRepository.save(commentEntity);
		commentRoom.triggerUpdate();
		return CommentResponseMapper.mapToCommentResponse(commentEntity);
	}

	public CommentPagingResponse getComments(CurrentUserContext user, Long roomId, Integer page, Integer size) {
		CommentRoomEntity commentRoom = getCommentRoom(roomId);

		Pageable pageable = PageRequestUtil.createPageRequest(page, size);
		Page<CommentEntity> comments = commentRepository.findLatestCommentsByRoomId(commentRoom.getId(), pageable);

		List<CommentResponse> commentResponses = comments.stream()
			.map(comment -> {
				comment.markAsRead(user.userRole());
				return CommentResponseMapper.mapToCommentResponse(comment);
			})
			.toList();
		PageResponse pageResponse = PageResponseMapper.mapToPageResponse(comments);
		return CommentResponseMapper.mapToCommentPagingResponse(commentResponses, pageResponse);
	}

	public void deleteComment(CurrentUserContext user, Long commentId) {
		CommentEntity comment = getComment(commentId);
		validateCommentWriter(user, comment);
		commentRepository.delete(comment);
	}

	public CommentResponse updateComment(CurrentUserContext user, Long commentId, String comment) {
		CommentEntity baseComment = getComment(commentId);
		validateCommentWriter(user, baseComment);
		baseComment.updateComment(comment);
		return CommentResponseMapper.mapToCommentResponse(baseComment);
	}

	private CommentEntity getComment(Long commentId) {
		return commentRepository.findById(commentId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "Comment not found"));
	}

	private void validateUser(CurrentUserContext user, Long userId) {
		if (!user.userId().equals(userId)) {
			throw new CoreApiException(ErrorType.FORBIDDEN, "User is not correct");
		}
	}

	private void validateCommentWriter(CurrentUserContext user, CommentEntity comment) {
		if (!comment.isWriter(user.userId(), user.userRole())) {
			throw new CoreApiException(ErrorType.FORBIDDEN, "Comment writer is not correct");
		}
	}

	private CommentRoomEntity getCommentRoom(Long roomId) {
		return commentRoomRepository.findById(roomId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "Comment Room not found"));
	}

	private Page<CommentRoomEntity> getCommentRoomsByRole(CurrentUserContext userContext,
		Page<CommentRoomEntity> commentRooms, Pageable pageable) {
		if (userContext.userRole().equals(Role.ROLE_MENTEE))
			commentRooms = commentRoomRepository.findByMenteeIdOrderByUpdatedAtDesc(userContext.userId(), pageable);
		else if (userContext.userRole().equals(Role.ROLE_MENTOR))
			commentRooms = commentRoomRepository.findByMentorIdOrderByUpdatedAtDesc(userContext.userId(), pageable);
		return commentRooms;
	}
}
