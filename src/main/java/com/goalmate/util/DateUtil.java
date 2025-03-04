package com.goalmate.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class DateUtil {
	public static final String ZONE_ID = "Asia/Seoul";

	public static OffsetDateTime toOffsetDateTime(LocalDateTime dateTime) {
		return dateTime.atZone(java.time.ZoneId.of(ZONE_ID)).toOffsetDateTime();
	}
}
