package com.group.libraryapp.presentation.dto.book.request;

import com.group.libraryapp.domain.type.BookCategory;
import lombok.Getter;

@Getter
public class UpdateBookRequest {
    private String name;
    private String writer;
    private String description;
    private BookCategory category;
    private Integer price;
}
