package com.goalmate.api.v2.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.goalmate.api.v2.dto.request.PageCondition;
import com.goalmate.api.v2.dto.response.PageResponse;
import com.goalmate.api.v2.dto.response.ParticipationResponse;
import com.goalmate.repository.MenteeGoalRepository;
import com.goalmate.service.MenteeGoalService;
import com.goalmate.util.PageRequestUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ParticipationService {
	private final MenteeGoalService menteeGoalService;
	private final MenteeGoalRepository menteeGoalRepository;

	public PageResponse<ParticipationResponse> getParticipationsByGoal(Long goalId, PageCondition pageCondition) {
		final Pageable pageable = PageRequestUtil.createPageRequest(pageCondition.getPage(), pageCondition.getSize());
		final List<ParticipationResponse> participations = menteeGoalRepository.findByGoalId(goalId, pageable)
			.stream()
			.map(participation ->
				ParticipationResponse.of(
					participation,
					menteeGoalService.getTodoProgress(participation)))
			.toList();
		return PageResponse.of(
			PageableExecutionUtils.getPage(
				participations,
				pageable,
				() -> menteeGoalRepository.countByGoalId(goalId))
		);
	}
}
