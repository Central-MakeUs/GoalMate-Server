package com.goalmate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.admin.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
	Optional<AdminEntity> findByLoginId(String id);

}
