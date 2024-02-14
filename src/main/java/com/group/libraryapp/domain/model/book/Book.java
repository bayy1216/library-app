package com.group.libraryapp.domain.model.book;

import com.group.libraryapp.core.type.BookCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Book {
    private final Long id;

    private final String name;

    private final String writer;

    private final String description;

    private final BookCategory category;

    private final Integer price;

    private final Integer stock;

    private final LocalDate publishedDate;

    @Builder
    public Book(Long id, String name, String writer, String description, BookCategory category, Integer price, Integer stock, LocalDate publishedDate) {
        this.id = id;
        this.name = name;
        this.writer = writer;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.publishedDate = publishedDate;
    }

    public static Book from(BookCreate create){
        return Book.builder()
                .name(create.getName())
                .writer(create.getWriter())
                .description(create.getDescription())
                .category(create.getCategory())
                .price(create.getPrice())
                .stock(0)
                .publishedDate(create.getPublishedDate())
                .build();
    }

    public Book updateBook(String name, String writer, String description, BookCategory category, Integer price, LocalDate publishedDate) {
        return Book.builder()
                .id(this.id)
                .name(name)
                .writer(writer)
                .description(description)
                .category(category)
                .price(price)
                .stock(this.stock)
                .publishedDate(publishedDate)
                .build();
    }

    public Book update(BookUpdate update){
        return Book.builder()
                .id(this.id)
                .name(update.getName())
                .writer(update.getWriter())
                .description(update.getDescription())
                .category(update.getCategory())
                .price(update.getPrice())
                .stock(this.stock)
                .publishedDate(update.getPublishedDate())
                .build();
    }

    public Book updateStock(Integer stock) {
        if(this.stock + stock < 0) throw new IllegalArgumentException("재고가 부족합니다.");
        return Book.builder()
                .id(this.id)
                .name(this.name)
                .writer(this.writer)
                .description(this.description)
                .category(this.category)
                .price(this.price)
                .stock(this.stock + stock)
                .publishedDate(this.publishedDate)
                .build();
    }
}
