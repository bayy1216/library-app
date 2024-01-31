package com.group.libraryapp.dto.book.request;

import com.group.libraryapp.type.BookCategory;
import lombok.Getter;

@Getter
public class UpdateBookRequest {
    private String name;
    private String writer;
    private String description;
    private BookCategory category;
    private Integer price;
}
