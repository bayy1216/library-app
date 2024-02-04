package com.group.libraryapp.infrastructure.book;

import com.group.libraryapp.domain.model.book.Book;
import com.group.libraryapp.domain.type.BookCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String writer;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private BookCategory category;

    private Integer price;

    private Integer stock;

    @Column(name = "published_date")
    private LocalDate publishedDate;


    @Builder
    public BookEntity(Long id, String name, String writer, String description, BookCategory category, Integer price, Integer stock, LocalDate publishedDate) {
        this.id = id;
        this.name = name;
        this.writer = writer;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.publishedDate = publishedDate;
    }

    public static BookEntity fromDomain(Book book) {
        return BookEntity.builder()
                .id(book.getId())
                .name(book.getName())
                .writer(book.getWriter())
                .description(book.getDescription())
                .category(book.getCategory())
                .price(book.getPrice())
                .stock(book.getStock())
                .publishedDate(book.getPublishedDate())
                .build();
    }


    public Book toDomain() {
        return Book.builder()
                .id(id)
                .name(name)
                .writer(writer)
                .description(description)
                .category(category)
                .price(price)
                .stock(stock)
                .publishedDate(publishedDate)
                .build();
    }
}
