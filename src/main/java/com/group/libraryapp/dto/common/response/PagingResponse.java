package com.group.libraryapp.dto.common.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PagingResponse<T> {
    private Integer totalPage;
    private List<T> data;
}
