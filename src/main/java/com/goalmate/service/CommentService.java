package com.goalmate.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.api.model.CommentPagingResponse;
import com.goalmate.api.model.CommentResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.domain.comment.CommentEntity;
import com.goalmate.domain.mentee.Role;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.mapper.CommentResponseMapper;
import com.goalmate.mapper.PageResponseMapper;
import com.goalmate.repository.CommentRepository;
import com.goalmate.security.SecurityUtil;
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
	private final CommentRepository commentRepository;

	public CommentResponse addMenteeComment(Long menteeGoalId, String comment) {
		MenteeGoalEntity menteeGoal = menteeGoalService.getMenteeGoal(menteeGoalId);

		// 오늘 코멘트가 이미 작성되었는지 확인
		commentRepository.findMenteeCommentToday(menteeGoalId)
			.ifPresent(baseComment -> {
				log.warn("Comment Already Exists: {}", baseComment);
				throw new CoreApiException(ErrorType.CONFLICT, "Comment Already Exists");
			});

		CommentEntity commentEntity = CommentEntity.builder()
			.comment(comment)
			.writerId(menteeGoal.getMenteeEntity().getId())
			.writerName(menteeGoal.getMenteeEntity().getName())
			.writerRole(Role.ROLE_MENTEE)
			.menteeGoalEntity(menteeGoal)
			.build();
		commentRepository.save(commentEntity);

		return CommentResponseMapper.mapToCommentResponse(commentEntity);
	}

	public CommentPagingResponse getComments(Long menteeGoalId, Integer page, Integer size) {
		MenteeGoalEntity menteeGoal = menteeGoalService.getMenteeGoal(menteeGoalId);

		Pageable pageable = PageRequestUtil.createPageRequest(page, size);
		Page<CommentEntity> comments = commentRepository.findByMenteeGoalId(menteeGoalId, pageable);

		List<CommentResponse> commentResponses = comments.stream()
			.map(CommentResponseMapper::mapToCommentResponse)
			.toList();
		PageResponse pageResponse = PageResponseMapper.mapToPageResponse(comments);

		return CommentResponseMapper.mapToCommentPagingResponse(commentResponses, pageResponse);
	}

	public boolean hasNewComment(Long menteeGoalId) {
		return !commentRepository.findNotReadMentorComment(menteeGoalId).isEmpty();
	}

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
}
