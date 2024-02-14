package com.group.libraryapp.presentation.dto.book.response;

import com.group.libraryapp.domain.model.book.Book;
import com.group.libraryapp.core.type.BookCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookInfoDto {
    private Long id;
    private String name;
    private String writer;
    private String publishedDate;
    private BookCategory category;
    private Integer price;
    private Integer stock;

    public static BookInfoDto fromDomain(Book book){
        return BookInfoDto.builder()
                .id(book.getId())
                .name(book.getName())
                .writer(book.getWriter())
                .publishedDate(book.getPublishedDate().toString())
                .category(book.getCategory())
                .price(book.getPrice())
                .stock(book.getStock())
                .build();
    }
}
