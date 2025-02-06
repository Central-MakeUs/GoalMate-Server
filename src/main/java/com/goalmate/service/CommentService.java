package com.goalmate.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {
	private final CommentRepository commentRepository;

	public boolean hasNewComment(Long menteeGoalId) {
		return !commentRepository.findNotReadByMenteeGoalEntityId(menteeGoalId).isEmpty();
	}
}
