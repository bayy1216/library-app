package com.group.libraryapp.presentation.dto.book.request;

import com.group.libraryapp.domain.model.book.BookCreate;
import com.group.libraryapp.domain.model.book.BookUpdate;
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

    public BookCreate toDomain(){
        return BookCreate.builder()
                .name(name)
                .writer(writer)
                .description(description)
                .category(category)
                .price(price)
                .publishedDate(publishedDate)
                .build();
    }

    public BookUpdate toUpdateDomain(){
        return BookUpdate.builder()
                .name(name)
                .writer(writer)
                .description(description)
                .category(category)
                .price(price)
                .publishedDate(publishedDate)
                .build();
    }
}
