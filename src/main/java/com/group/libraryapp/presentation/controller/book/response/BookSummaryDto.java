package com.group.libraryapp.presentation.controller.book.response;

import com.group.libraryapp.domain.model.book.Book;
import com.group.libraryapp.core.type.BookCategory;
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

    public static BookSummaryDto fromDomain(Book book) {
        return BookSummaryDto.builder()
                .id(book.getId())
                .name(book.getName())
                .writer(book.getWriter())
                .publishedDate(book.getPublishedDate().toString())
                .category(book.getCategory())
                .price(book.getPrice())
                .build();
    }
}
