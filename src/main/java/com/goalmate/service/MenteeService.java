package com.goalmate.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.domain.mentee.MenteeEntity;
import com.goalmate.repository.MenteeRepository;
import com.goalmate.security.SecurityUtil;
import com.goalmate.support.error.CoreApiException;
import com.goalmate.support.error.ErrorType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MenteeService {
	private final MenteeRepository menteeRepository;

	public void updateMenteeName(String name) {
		validateMenteeName(name);
		findCurrentMentee().updateName(name);
	}

	@Transactional(readOnly = true)
	public void validateMenteeName(String name) {
		menteeRepository.findByName(name).ifPresent(menteeEntity -> {
			log.warn(">>>>> Mentee name already exists: " + name);
			throw new CoreApiException(ErrorType.CONFLICT, "Mentee name already exists: " + name);
		});
	}

	private MenteeEntity findCurrentMentee() {
		return menteeRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(() ->
			new CoreApiException(ErrorType.NOT_FOUND));
	}
}

