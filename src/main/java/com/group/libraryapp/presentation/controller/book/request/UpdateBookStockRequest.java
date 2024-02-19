package com.group.libraryapp.presentation.controller.book.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class UpdateBookStockRequest {
    @Positive(message = "재고는 0보다 커야 합니다.")
    @NotNull(message = "재고는 필수입니다.")
    private Integer stock;
}
