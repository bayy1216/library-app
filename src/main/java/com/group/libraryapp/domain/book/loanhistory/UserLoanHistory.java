package com.group.libraryapp.domain.book.loanhistory;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.type.LoanType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserLoanHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "book_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Enumerated(EnumType.STRING)
    private LoanType type;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Builder
    public UserLoanHistory(User user, Book book, LoanType type) {
        this.user = user;
        this.book = book;
        this.type = type;
        this.createdDate = LocalDate.now();
    }

    /**
     * 책의 대출기록을 반납처리한다.
     * [type]을 RETURNED로 변경하고, [book]의 재고를 1 증가시킨다.
     */
    public void doReturnBook() {
        this.type = LoanType.RETURNED;
        this.book.addStock(1);
    }

}
