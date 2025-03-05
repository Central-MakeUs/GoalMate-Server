package com.goalmate.api.v2.dto.response.goal;

public record MidObjectiveResponse(
	int sequence,
	String description
) {
	public static MidObjectiveResponse from(com.goalmate.domain.goal.MidObjectiveEntity midObjective) {
		return new MidObjectiveResponse(
			midObjective.getSequence(),
			midObjective.getDescription()
		);
	}
}
