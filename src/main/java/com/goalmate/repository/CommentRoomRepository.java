package com.goalmate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.comment.CommentRoomEntity;

public interface CommentRoomRepository extends JpaRepository<CommentRoomEntity, Long> {

	Page<CommentRoomEntity> findByMenteeIdOrderByUpdatedAtDesc(Long menteeId, Pageable pageable);

	Page<CommentRoomEntity> findByMentorIdOrderByUpdatedAtDesc(Long mentorId, Pageable pageable);
}
