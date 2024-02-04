package com.group.libraryapp.domain.book;

import com.group.libraryapp.type.BookCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {
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
    public Book(String name, String writer, String description, BookCategory category, Integer price, Integer stock, LocalDate publishedDate) {
        this.name = name;
        this.writer = writer;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.publishedDate = publishedDate;
    }

    public void updateBook(String name, String writer, String description, BookCategory category, Integer price, LocalDate publishedDate) {
        this.name = name;
        this.writer = writer;
        this.description = description;
        this.category = category;
        this.price = price;
        this.publishedDate = publishedDate;
    }

    public void addStock(Integer stock) {
        assert stock > 0;
        this.stock += stock;
    }

    /**
     * 재고를 빼는 메소드. 재고가 부족하면 IllegalArgumentException을 던진다.
     */
    public void subtractStock(Integer stock) {
        assert stock > 0;
        if(this.stock < stock) throw new IllegalArgumentException("재고가 부족합니다.");
        this.stock -= stock;
    }
}
