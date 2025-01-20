package com.goalmate.mapper;

import org.springframework.data.domain.Page;

import com.goalmate.api.model.PageResponse;

public class PageResponseMapper {

	public static <T> PageResponse mapToPageResponse(Page<T> page) {
		PageResponse pageResponse = new PageResponse();
		pageResponse.setTotalPages(page.getTotalPages());
		pageResponse.setTotalElements(Long.valueOf(page.getTotalElements()).intValue());
		pageResponse.setCurrentPage(page.getPageable().getPageNumber() + 1);
		pageResponse.setPageSize(page.getPageable().getPageSize());
		pageResponse.setNextPage(page.hasNext() ? page.getPageable().getPageNumber() + 2 : null);
		pageResponse.setPrevPage(page.hasPrevious() ? page.getPageable().getPageNumber() : null);
		return pageResponse;
	}
	
}
