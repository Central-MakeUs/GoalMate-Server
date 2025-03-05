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

	@Transactional(readOnly = true)
	public MenteeInfoResponse getMenteeInfo(Long menteeId) {
		MenteeEntity mentee = getMenteeById(menteeId);
		List<MenteeGoalEntity> menteeGoals = menteeGoalRepository.findByMenteeId(menteeId);
		return MenteeResponseMapper.mapToMenteeInfoResponse(mentee, menteeGoals);
	}

	public String updateMenteeName(Long menteeId, String name) {
		if (!isNameAvailable(name)) {
			throw new CoreApiException(ErrorType.CONFLICT, "Mentee name is not valid: " + name);
		}
		getMenteeById(menteeId).updateName(name);
		return name;
	}

	@Transactional(readOnly = true)
	public boolean isNameAvailable(String name) {
		// 길이 제한 (2~5자)
		if (name.length() < 2 || name.length() > 5) {
			return false;
		}
		
		// for (char ch : name.toCharArray()) {
		// 	// 한글 자음/모음 단독 사용 불가
		// 	if (ch >= 'ㄱ' && ch <= 'ㅣ') {
		// 		return false;
		// 	}
		// 	// 특수문자 검사 (한글, 영문, 숫자만 허용)
		// 	if (!name.matches("^[가-힣a-zA-Z0-9]+$")) {
		// 		return false;
		// 	}
		// }
		return !menteeRepository.existsByName(name);
	}

	@Transactional(readOnly = true)
	public MenteeEntity getMenteeById(Long menteeId) {
		return menteeRepository.findById(menteeId).orElseThrow(() ->
			new CoreApiException(ErrorType.NOT_FOUND, "Mentee not found: " + menteeId));
	}

}

