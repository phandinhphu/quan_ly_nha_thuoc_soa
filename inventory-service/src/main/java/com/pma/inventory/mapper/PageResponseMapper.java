package com.pma.inventory.mapper;

import org.springframework.data.domain.Page;

import com.pma.inventory.dto.paginate.PageResponse;
import com.pma.inventory.dto.paginate.PaginationMeta;

public class PageResponseMapper {
	
	public static <T> PageResponse<T> from(Page<T> page) {
        PaginationMeta meta = new PaginationMeta();
        meta.setPage(page.getNumber());
        meta.setSize(page.getSize());
        meta.setTotalItems(page.getTotalElements());
        meta.setTotalPages(page.getTotalPages());

        PageResponse<T> res = new PageResponse<>();
        res.setItems(page.getContent());
        res.setPagination(meta);
        return res;
    }

}
