package com.goalmate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.api.model.MenteeInfoResponse;
import com.goalmate.domain.mentee.MenteeEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.mapper.MenteeResponseMapper;
import com.goalmate.repository.MenteeGoalRepository;
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
	private final MenteeGoalRepository menteeGoalRepository;

	public MenteeInfoResponse getMenteeInfo(Long menteeId) {
		MenteeEntity mentee = getMenteeById(menteeId);
		List<MenteeGoalEntity> menteeGoals = menteeGoalRepository.findByMenteeEntityId(menteeId);
		return MenteeResponseMapper.mapToMenteeInfoResponse(mentee, menteeGoals);
	}

	public String updateMenteeName(Long menteeId, String name) {
		validateMenteeName(name);
		getMenteeById(menteeId).updateName(name);
		return name;
	}

	@Transactional(readOnly = true)
	public void validateMenteeName(String name) {
		menteeRepository.findByName(name).ifPresent(menteeEntity -> {
			log.warn(">>>>> Mentee name already exists: " + name);
			throw new CoreApiException(ErrorType.CONFLICT, "Mentee name already exists: " + name);
		});
	}

	private MenteeEntity getMenteeById(Long menteeId) {
		return menteeRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(() ->
			new CoreApiException(ErrorType.NOT_FOUND));
	}
}

