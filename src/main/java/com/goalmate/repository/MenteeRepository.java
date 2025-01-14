package com.goalmate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.mentee.Mentee;

public interface MenteeRepository extends JpaRepository<Mentee, Long> {
	Optional<Mentee> findBySocialId(String socialId);
}
