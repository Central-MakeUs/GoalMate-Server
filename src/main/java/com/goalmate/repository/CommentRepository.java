package com.goalmate.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goalmate.domain.comment.CommentEntity;
import com.goalmate.domain.mentee.Role;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

	@Query("SELECT c FROM CommentEntity c WHERE c.commentRoom.id = :roomId ORDER BY c.createdAt DESC ")
	Page<CommentEntity> findLatestCommentsByRoomId(Long roomId, Pageable pageable);

	@Query("SELECT c FROM CommentEntity c "
		+ "WHERE c.writerRole = com.goalmate.domain.mentee.Role.ROLE_MENTEE "
		+ "AND c.commentRoom.id = :roomId "
		+ "AND c.commentType = com.goalmate.domain.comment.CommentType.DAILY "
		+ "AND c.createdAt BETWEEN :startOfDay AND :endOfDay")
	Optional<CommentEntity> findTodayCommentFromMentee(
		Long roomId,
		LocalDateTime startOfDay,
		LocalDateTime endOfDay
	);

	@Query("SELECT COUNT(c) FROM CommentEntity c "
		+ "WHERE c.writerRole = :writerRole "
		+ "AND c.commentRoom.id = :roomId "
		+ "AND c.isRead = false")
	Long countUnreadComments(Long roomId, Role writerRole);
}
