package com.goalmate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.comment.CommentRoomEntity;

public interface CommentRoomRepository extends JpaRepository<CommentRoomEntity, Long> {

	// TODO: comment를 변경했을때 room 의 updatedAt을 변경해야함
	Page<CommentRoomEntity> findByMenteeIdOrderByUpdatedAtDesc(Long menteeId, Pageable pageable);

	Page<CommentRoomEntity> findByMentorIdOrderByUpdatedAtDesc(Long mentorId, Pageable pageable);
}
