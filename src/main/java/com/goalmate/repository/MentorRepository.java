package com.goalmate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.mentor.MentorEntity;

public interface MentorRepository extends JpaRepository<MentorEntity, Long> {
	Optional<MentorEntity> findByEmail(String email);
}
