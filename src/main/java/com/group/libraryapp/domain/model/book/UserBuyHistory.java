package com.group.libraryapp.domain.model.book;

import com.group.libraryapp.domain.model.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
@Getter
public class UserBuyHistory {

    private final Long id;

    private final User user;

    private final Book book;

    private final LocalDate createdDate;

    @Builder
    public UserBuyHistory(Long id, User user, Book book, LocalDate createdDate) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.createdDate = createdDate;
    }
}
