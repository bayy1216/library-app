package com.group.libraryapp.dto.book.response;

import com.group.libraryapp.type.BookCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookSummaryDto {
    private Long id;
    private String name;
    private String writer;
    private String publishedDate;
    private BookCategory category;
    private Integer price;
}
