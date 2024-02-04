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

    /**
     * [User]가 책을 구매하는 행위를 한다.
     * [money]를 확인하여 구매가능한지 확인한 뒤, 책의 재고를 감소하고, 구매기록을 추가한다.
     */
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
     * [User]가 책을 대여하는 행위를 한다.
     * [userCurrentLoanHistories]의 개수를 확인하여 대여가능한지 확인한 뒤, 대출기록을 추가한다.
     */
    public void loanBook(Book book, List<UserLoanHistory> userCurrentLoanHistories) {
        if(userCurrentLoanHistories.size() >= 10) {
            throw new IllegalArgumentException("대여 가능한 책의 수를 초과하였습니다.");
        }
        book.subtractStock(1);
        UserLoanHistory userLoanHistory = UserLoanHistory.builder()
                .user(this)
                .book(book)
                .type(LoanType.LOANED)
                .build();
        this.userLoanHistories.add(userLoanHistory);
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
