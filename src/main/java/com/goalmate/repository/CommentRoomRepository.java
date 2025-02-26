package com.goalmate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goalmate.domain.comment.CommentRoomEntity;

public interface CommentRoomRepository extends JpaRepository<CommentRoomEntity, Long> {

	Page<CommentRoomEntity> findByMenteeIdOrderByUpdatedAtDesc(Long menteeId, Pageable pageable);

	Page<CommentRoomEntity> findByMentorIdOrderByUpdatedAtDesc(Long mentorId, Pageable pageable);

	@Query("SELECT cr FROM CommentRoomEntity cr WHERE cr.menteeGoal.id = :participationId ORDER BY cr.updatedAt DESC")
	Page<CommentRoomEntity> findByParticipationId(Long participationId, Pageable pageable);

	@Query("SELECT COUNT(cr) FROM CommentRoomEntity cr WHERE cr.menteeGoal.id = :participationId")
	long countByParticipationId(Long participationId);
}
