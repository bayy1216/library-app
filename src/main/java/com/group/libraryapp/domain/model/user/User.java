package com.group.libraryapp.domain.model.user;


import com.group.libraryapp.domain.model.book.Book;
import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.core.type.LoanType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class User {
    private final Long id;

    private final String name;

    private final Integer age;

    private final Integer money;

    private final String email;

    private final String password;

    @Builder
    public User (Long id, String name, Integer age, Integer money, String email, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.money = money;
        this.email = email;
        this.password = password;
    }

    public static User from(UserCreate create) {
        return User.builder()
                .name(create.getName())
                .age(create.getAge())
                .money(0)
                .email(create.getEmail())
                .password(create.getPassword())
                .build();
    }

    public User chargeMoney(Integer money) {
        return User.builder()
                .id(this.id)
                .name(this.name)
                .age(this.age)
                .money(this.money + money)
                .email(this.email)
                .password(this.password)
                .build();
    }

    public UserBuyHistory buyBook(Book book) {
        if (this.money < book.getPrice()) {
            throw new IllegalArgumentException("돈이 부족합니다.");
        }
        User buyer = User.builder()
                .id(this.id)
                .name(this.name)
                .age(this.age)
                .money(this.money - book.getPrice())
                .email(this.email)
                .password(this.password)
                .build();
        return UserBuyHistory.builder()
                .book(book.updateStock(-1))
                .user(buyer)
                .build();
    }

    public UserLoanHistory loanBook(Book book, List<UserLoanHistory> userCurrentLoanHistories) {
        if (userCurrentLoanHistories.size() >= 10) {
            throw new IllegalArgumentException("대여 가능한 책의 수를 초과하였습니다.");
        }
        return UserLoanHistory.builder()
                .book(book.updateStock(-1))
                .user(this)
                .build();
    }

    public UserLoanHistory returnBook(UserLoanHistory userLoanHistory) {
        return UserLoanHistory.builder()
                .id(userLoanHistory.getId())
                .book(userLoanHistory.getBook().updateStock(1))
                .type(LoanType.RETURNED)
                .createdDate(userLoanHistory.getCreatedDate())
                .user(this)
                .build();
    }
}
