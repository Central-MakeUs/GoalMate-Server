package com.goalmate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.mentee.MenteeEntity;

public interface MenteeRepository extends JpaRepository<MenteeEntity, Long> {
	Optional<MenteeEntity> findBySocialId(String socialId);

	boolean existsByName(String name);

	Optional<MenteeEntity> findByName(String name);
}
