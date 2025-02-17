package com.goalmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.mentor.MentorEntity;

public interface MentorRepository extends JpaRepository<MentorEntity, Long> {
}
