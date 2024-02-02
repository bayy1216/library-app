package com.group.libraryapp.domain.book.buyhistory;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.user.User;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserBuyHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "book_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Builder
    public UserBuyHistory(User user, Book book) {
        this.user = user;
        this.book = book;
        this.createdDate = LocalDate.now();
    }
}
