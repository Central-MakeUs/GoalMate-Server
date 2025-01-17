package com.goalmate.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goalmate.domain.menteeGoal.MenteeGoalDailyCommentEntity;

public interface MenteeGoalDailyCommentRepository extends JpaRepository<MenteeGoalDailyCommentEntity, Long> {
	@Query("SELECT m FROM MenteeGoalDailyCommentEntity m WHERE m.menteeGoalEntity.id = :menteeGoalId")
	List<MenteeGoalDailyCommentEntity> findByMenteeGoalId(Long menteeGoalId);

	@Query("SELECT m FROM MenteeGoalDailyCommentEntity m WHERE m.menteeGoalEntity.id = :menteeGoalId AND m.commentDate = :date")
	Optional<MenteeGoalDailyCommentEntity> findByMenteeGoalIdAndDate(Long menteeGoalId, LocalDate date);
}
