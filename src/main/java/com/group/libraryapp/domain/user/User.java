package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.buyhistory.UserBuyHistory;
import com.group.libraryapp.domain.book.loanhistory.UserLoanHistory;
import com.group.libraryapp.type.LoanType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    private Integer age;

    private Integer money;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserBuyHistory> userBuyHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

    /**
     * [User] 생성하기 위한 builder.
     * 처음에 money는 0으로 초기화
     */
    @Builder
    public User(String name, Integer age, String email, String password) {
        this.name = name;
        this.age = age;
        this.money = 0;
        this.email = email;
        this.password = password;
    }



    public void chargeMoney(Integer money) {
        assert money > 0;
        this.money += money;
    }

    public void buyBook(Book book) {
        if(this.money < book.getPrice()) {
            throw new IllegalArgumentException("돈이 부족합니다.");
        }
        this.money -= book.getPrice();
        book.subtractStock(1);
        UserBuyHistory userBuyHistory = UserBuyHistory.builder()
                .user(this)
                .book(book)
                .build();
        this.userBuyHistories.add(userBuyHistory);
    }



    /**
     * [User]가 책을 반납하는 행위를 한다.
     * [userLoanHistory]의 반납여부를 확인한 뒤, 대출기록을 반납처리한다.
     */
    public void returnBook(UserLoanHistory userLoanHistory) {
        if(userLoanHistory.getType() == LoanType.RETURNED) {
            throw new IllegalArgumentException("이미 반납된 책입니다.");
        }
        userLoanHistory.doReturnBook();
    }
}
