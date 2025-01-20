package com.goalmate.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageRequestUtil {
	public static Pageable createPageRequest(Integer page, Integer size) {
		// Input 기본값은 1부터 시작이지만, PageRequest.of()는 0부터 시작
		return PageRequest.of(page - 1, size);
	}
}
