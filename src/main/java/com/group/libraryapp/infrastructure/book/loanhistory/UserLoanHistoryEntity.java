package com.group.libraryapp.infrastructure.book.loanhistory;

import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.infrastructure.book.BookEntity;
import com.group.libraryapp.infrastructure.user.UserEntity;
import com.group.libraryapp.domain.type.LoanType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserLoanHistoryEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @JoinColumn(name = "book_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private BookEntity book;

    @Enumerated(EnumType.STRING)
    private LoanType type;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Builder
    public UserLoanHistoryEntity(UserEntity user, BookEntity book, LoanType type, LocalDate createdDate) {
        this.user = user;
        this.book = book;
        this.type = type;
        this.createdDate = createdDate;
    }

    /**
     * 책의 대출기록을 반납처리한다.
     * [type]을 RETURNED로 변경하고, [book]의 재고를 1 증가시킨다.
     */
    public void doReturnBook() {
        this.type = LoanType.RETURNED;
        this.book.addStock(1);
    }

    public UserLoanHistory toDomain() {
        return UserLoanHistory.builder()
                .id(this.id)
                .user(this.user.toDomain())
                .book(this.book.toDomain())
                .type(this.type)
                .createdDate(this.createdDate)
                .build();
    }
    public static UserLoanHistoryEntity fromDomain(UserLoanHistory userLoanHistory) {
        return UserLoanHistoryEntity.builder()
                .user(UserEntity.fromDomain(userLoanHistory.getUser()))
                .book(BookEntity.fromDomain(userLoanHistory.getBook()))
                .type(userLoanHistory.getType())
                .createdDate(userLoanHistory.getCreatedDate())
                .build();
    }

}
