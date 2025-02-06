package com.goalmate.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goalmate.domain.comment.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	@Query("SELECT c FROM CommentEntity c WHERE c.menteeGoalEntity.id = :menteeGoalId")
	List<CommentEntity> findByMenteeGoalId(Long menteeGoalId);

	@Query("SELECT c FROM CommentEntity c WHERE c.menteeGoalEntity.id = :menteeGoalId AND c.commentDate = :date")
	Optional<CommentEntity> findByMenteeGoalIdAndDate(Long menteeGoalId, LocalDate date);

	@Query("SELECT c FROM CommentEntity c WHERE c.menteeGoalEntity.id = :menteeGoalId AND c.isRead = false")
	List<CommentEntity> findNotReadByMenteeGoalEntityId(Long menteeGoalId);
}
