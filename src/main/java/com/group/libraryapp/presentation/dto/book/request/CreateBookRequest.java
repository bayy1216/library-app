package com.group.libraryapp.presentation.dto.book.request;

import com.group.libraryapp.domain.model.book.BookCreate;
import com.group.libraryapp.domain.model.book.BookUpdate;
import com.group.libraryapp.core.type.BookCategory;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
public class CreateBookRequest {
    @NotBlank(message = "책 이름은 필수입니다.")
    private String name;
    @NotBlank(message = "작가는 필수입니다.")
    private String writer;
    @NotBlank(message = "설명은 필수입니다.")
    private String description;
    @NotNull(message = "카테고리는 필수입니다.")
    private BookCategory category;
    @NotNull(message = "가격은 필수입니다.")
    @Positive(message = "가격은 0보다 커야 합니다.")
    private Integer price;
    @NotNull(message = "출판일은 필수입니다.")
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
