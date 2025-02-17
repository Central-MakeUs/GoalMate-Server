package com.goalmate.mapper;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import com.goalmate.api.model.GoalDetailResponse;
import com.goalmate.api.model.GoalStatusEnum;
import com.goalmate.api.model.GoalSummaryPagingResponse;
import com.goalmate.api.model.GoalSummaryResponse;
import com.goalmate.api.model.ImageResponse;
import com.goalmate.api.model.MidObjectiveResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.api.model.WeeklyObjectiveResponse;
import com.goalmate.domain.goal.GoalEntity;

public class GoalResponseMapper {
	private static final int CLOSING_SOON_THRESHOLD = 10;

	public static GoalSummaryResponse mapToSummaryResponse(GoalEntity goal) {
		GoalSummaryResponse summary = new GoalSummaryResponse();
		summary.setId(goal.getId().intValue());
		summary.setTitle(goal.getTitle());
		summary.setTopic(goal.getTopic());
		summary.setDescription(goal.getDescription());
		summary.setPeriod(goal.getPeriod());
		summary.setDailyDuration(goal.getDailyDuration());
		// summary.setPrice(goal.getPrice());
		// summary.setDiscountPrice(goal.getDiscountPrice());
		summary.setParticipantsLimit(goal.getParticipantsLimit());
		summary.setCurrentParticipants(goal.getCurrentParticipants());
		summary.setIsClosingSoon(goal.getCurrentParticipants() <= CLOSING_SOON_THRESHOLD);
		summary.setGoalStatus(GoalStatusEnum.fromValue(goal.getGoalStatus().getValue()));
		summary.setMentorName(goal.getMentor().getName());
		summary.setCreatedAt(goal.getCreatedAt().atOffset(ZoneOffset.UTC));
		summary.setUpdatedAt(goal.getUpdatedAt().atOffset(ZoneOffset.UTC));
		goal.getThumbnailImages().stream()
			.findFirst().ifPresent(thumbnailImage -> summary.setMainImage(thumbnailImage.getImageUrl()));
		return summary;
	}

	public static GoalDetailResponse mapToDetailResponse(GoalEntity goal) {
		GoalDetailResponse detail = new GoalDetailResponse();
		detail.setId(goal.getId().intValue());
		detail.setTitle(goal.getTitle());
		detail.setTopic(goal.getTopic());
		detail.setDescription(goal.getDescription());
		detail.setPeriod(goal.getPeriod());
		// detail.setPrice(goal.getPrice());
		// detail.setDiscountPrice(goal.getDiscountPrice());
		detail.setParticipantsLimit(goal.getParticipantsLimit());
		detail.setCurrentParticipants(goal.getCurrentParticipants());
		detail.setGoalStatus(GoalStatusEnum.fromValue(goal.getGoalStatus().getValue()));
		detail.setMentorName(goal.getMentor().getName());
		detail.setCreatedAt(goal.getCreatedAt().atOffset(ZoneOffset.UTC));
		detail.setUpdatedAt(goal.getUpdatedAt().atOffset(ZoneOffset.UTC));
		// ThumbnailImages
		detail.setThumbnailImages(goal.getThumbnailImages().stream()
			.map(thumbnailImage -> mapToImageResponse(thumbnailImage.getSequence(), thumbnailImage.getImageUrl()))
			.toList());
		// ContentImages
		detail.setContentImages(goal.getContentImages().stream()
			.map(contentImage -> mapToImageResponse(contentImage.getSequence(), contentImage.getImageUrl()))
			.toList());

		// Weekly Objectives
		detail.setWeeklyObjectives(goal.getWeeklyObjective().stream()
			.map(weeklyObjective -> {
				WeeklyObjectiveResponse weeklyObjectiveResponse = new WeeklyObjectiveResponse();
				weeklyObjectiveResponse.setWeekNumber(weeklyObjective.getWeekNumber());
				weeklyObjectiveResponse.setDescription(weeklyObjective.getDescription());
				return weeklyObjectiveResponse;
			})
			.collect(Collectors.toList()));
		// Mid-Objectives
		detail.setMidObjectives(goal.getMidObjective().stream()
			.map(midObjective -> {
				MidObjectiveResponse midObjectiveResponse = new MidObjectiveResponse();
				midObjectiveResponse.setDescription(midObjective.getDescription());
				return midObjectiveResponse;
			})
			.collect(Collectors.toList()));
		return detail;
	}

	public static GoalSummaryPagingResponse mapToSummaryPagingResponse(
		List<GoalSummaryResponse> summaries,
		PageResponse pageResponse) {

		GoalSummaryPagingResponse response = new GoalSummaryPagingResponse();
		response.setGoals(summaries);
		response.setPage(pageResponse);
		return response;
	}

	private static ImageResponse mapToImageResponse(Integer sequence, String imageUrl) {
		ImageResponse imageResponse = new ImageResponse();
		imageResponse.setSequence(sequence);
		imageResponse.setImageUrl(imageUrl);
		return imageResponse;
	}
}
