package com.group.libraryapp.dto.common.response;

import lombok.Builder;
import lombok.Getter;

@Builder
public class PagingResponse<T> {
    private Integer totalPage;
    private T data;
}
