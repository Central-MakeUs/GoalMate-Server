package com.goalmate.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.domain.mentor.MentorEntity;
import com.goalmate.repository.MentorRepository;
import com.goalmate.support.error.CoreApiException;
import com.goalmate.support.error.ErrorType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class MentorService {
	private final MentorRepository mentorRepository;

	public MentorEntity getMentorById(Long mentorId) {
		return mentorRepository.findById(mentorId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "Mentor not found"));
	}
}
