package com.group.libraryapp.domain.model.book;

import com.group.libraryapp.domain.type.BookCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class BookUpdate {

    private String name;
    private String writer;
    private String description;
    private BookCategory category;
    private Integer price;
    private LocalDate publishedDate;
}
