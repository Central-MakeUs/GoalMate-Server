package com.goalmate.api.v2.dto.request;

import com.goalmate.util.PageValue;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageCondition {
	private Integer page;
	private Integer size;

	public PageCondition(Integer page, Integer size) {
		this.page = isValidPage(page) ? page : PageValue.DEFAULT_PAGE.getValue();
		this.size = isValidSize(size) ? size : PageValue.DEFAULT_SIZE.getValue();
	}

	private Boolean isValidPage(Integer page) {
		return page != null && page > 0;
	}

	private Boolean isValidSize(Integer size) {
		return size != null && size > 0;
	}
}

