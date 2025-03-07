package com.goalmate.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.goal.GoalStatus;

public interface GoalRepository extends JpaRepository<GoalEntity, Long> {

	@Query("SELECT g FROM GoalEntity g WHERE g.goalStatus <> :excludedStatus")
	Page<GoalEntity> findAllExcludingStatus(@Param("excludedStatus") GoalStatus excludedStatus, Pageable pageable);

	@Query("SELECT g FROM GoalEntity g " +
		"LEFT JOIN FETCH g.dailyTodos " +
		"WHERE g.id = :id")
	Optional<GoalEntity> findByIdWithDetails(@Param("id") Long id);
}
