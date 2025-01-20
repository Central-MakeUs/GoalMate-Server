package com.goalmate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goalmate.domain.menteeGoal.MenteeGoalEntity;

public interface MenteeGoalRepository extends JpaRepository<MenteeGoalEntity, Long> {
	@Query("SELECT COUNT(m) FROM MenteeGoalEntity m WHERE m.goalEntity.id = :goalId")
	Long countByGoalId(Long goalId);

	@Query("SELECT m FROM MenteeGoalEntity m WHERE m.menteeEntity.id = :menteeId")
	List<MenteeGoalEntity> findByMenteeId(Long menteeId);

	@Query("SELECT m FROM MenteeGoalEntity m WHERE m.menteeEntity.id = :menteeId")
	Page<MenteeGoalEntity> findByMenteeId(Long menteeId, Pageable pageable);

	@Query("SELECT m FROM MenteeGoalEntity m WHERE m.menteeEntity.id = :menteeId AND m.goalEntity.id = :goalId")
	Optional<MenteeGoalEntity> findByMenteeIdAndGoalId(Long menteeId, Long goalId);
}
