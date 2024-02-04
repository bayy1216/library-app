package com.group.libraryapp.domain.model.book;

import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.domain.type.LoanType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserLoanHistory {
    private final Long id;

    private final User user;

    private final Book book;

    private final LoanType type;

    private final LocalDate createdDate;

    @Builder
    public UserLoanHistory(Long id, User user, Book book, LoanType type, LocalDate createdDate) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.type = type;
        this.createdDate = LocalDate.now();
    }


}
