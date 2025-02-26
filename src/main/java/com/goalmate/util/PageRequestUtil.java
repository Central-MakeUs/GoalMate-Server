package com.goalmate.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageRequestUtil {
	public static Pageable createPageRequest(Integer page, Integer size) {
		return PageRequest.of(page - PageValue.DEFAULT_PAGE.getValue(), size);
	}
}
