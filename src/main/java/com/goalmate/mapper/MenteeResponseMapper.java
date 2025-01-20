package com.goalmate.mapper;

import static com.goalmate.api.model.MenteeInfoResponse.*;

import java.util.List;

import com.goalmate.api.model.MenteeInfoResponse;
import com.goalmate.domain.mentee.MenteeEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenteeResponseMapper {
	public static MenteeInfoResponse mapToMenteeInfoResponse(MenteeEntity mentee, List<MenteeGoalEntity> menteeGoals) {
		final MenteeInfoResponse response = new MenteeInfoResponse();
		int inProgressCount = 0;
		int completedCount = 0;
		int cancelledCount = 0;
		for (MenteeGoalEntity menteeGoal : menteeGoals) {
			switch (menteeGoal.getStatus()) {
				case IN_PROGRESS:
					inProgressCount++;
					break;
				case COMPLETED:
					completedCount++;
					break;
				case CANCELLED:
					cancelledCount++;
					break;
			}
		}
		response.setId(mentee.getId().intValue());
		response.setName(mentee.getName());
		response.setInProgressGoalCount(inProgressCount);
		response.setCompletedGoalCount(completedCount);
		response.setFreeParticipationCount(mentee.getFreeParticipationCount());
		response.setMenteeStatus(MenteeStatusEnum.fromValue(mentee.getStatus().name()));
		return response;
	}

}
