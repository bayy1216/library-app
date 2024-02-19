package com.group.libraryapp.presentation.controller.common.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PagingResponse<T> {
    private Integer totalPage;
    private List<T> data;

    public PagingResponse(Integer totalPage, List<T> data) {
        this.totalPage = totalPage;
        this.data = data;
    }

    public static <T> PagingResponse<T> fromPage(Page<T> page) {
        return new PagingResponse<>(page.getTotalPages(), page.getContent());
    }
}
