package com.group.libraryapp.presentation.dto.book.request;

import com.group.libraryapp.domain.type.BookCategory;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateBookRequest {
    private String name;
    private String writer;
    private String description;
    private BookCategory category;
    private Integer price;
    private LocalDate publishedDate;
}
