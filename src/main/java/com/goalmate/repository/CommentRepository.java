package com.goalmate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goalmate.domain.comment.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

	@Query("SELECT c FROM CommentEntity c WHERE c.menteeGoalEntity.id = :menteegGoalId ORDER BY c.createdAt DESC ")
	Page<CommentEntity> findByMenteeGoalId(Long menteeGoalId, Pageable pageable);

	// TODO: 이거 오늘 하루종일로 할 수 있어야함 : AND c.createdAt = :date
	@Query("SELECT c FROM CommentEntity c WHERE c.isMentor = false AND c.menteeGoalEntity.id = :menteeGoalId AND c.commentType = 'DAILY'")
	Optional<CommentEntity> findMenteeCommentToday(Long menteeGoalId);

	@Query("SELECT c FROM CommentEntity c WHERE c.isMentor = true AND c.menteeGoalEntity.id = :menteeGoalId AND c.isRead = false")
	List<CommentEntity> findNotReadMentorComment(Long menteeGoalId);

}
