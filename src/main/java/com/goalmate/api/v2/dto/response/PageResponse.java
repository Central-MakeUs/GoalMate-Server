package com.goalmate.api.v2.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.goalmate.util.PageValue;

public record PageResponse<T>(
	Integer totalPages,
	Integer currentPage,
	Integer pageSize,
	Integer nextPage,
	Integer prevPage,
	Boolean hasNext,
	Boolean hasPrev,
	List<T> content) {

	public static <T> PageResponse<T> of(Page<T> page) {
		return new PageResponse<>(
			page.getTotalPages(),
			page.getPageable().getPageNumber() + PageValue.DEFAULT_PAGE.getValue(),
			page.getPageable().getPageSize(),
			page.getPageable().getPageNumber() + PageValue.DEFAULT_PAGE.getValue() + 1,
			page.getPageable().getPageNumber(),
			page.hasNext(),
			page.hasPrevious(),
			page.getContent()
		);
	}

	public static <T> PageResponse<T> of(List<T> content, Integer totalPages, Integer currentPage, Integer pageSize) {
		return new PageResponse<>(
			totalPages,
			currentPage,
			pageSize,
			currentPage + 1,
			currentPage - 1,
			currentPage < totalPages,
			currentPage > 1,
			content
		);
	}

	@Override
	public String toString() {
		return "PageResponse[" +
			"totalPages=" + totalPages + ", " +
			"currentPage=" + currentPage + ", " +
			"pageSize=" + pageSize + ", " +
			"nextPage=" + nextPage + ", " +
			"prevPage=" + prevPage + ", " +
			"hasNext=" + hasNext + ", " +
			"hasPrev=" + hasPrev + ", " +
			"content=" + content + ']';
	}
}
