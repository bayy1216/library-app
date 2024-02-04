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
    public UserLoanHistoryEntity(Long id, UserEntity user, BookEntity book, LoanType type, LocalDate createdDate) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.type = type;
        this.createdDate = createdDate;
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
                .id(userLoanHistory.getId())
                .user(UserEntity.fromDomain(userLoanHistory.getUser()))
                .book(BookEntity.fromDomain(userLoanHistory.getBook()))
                .type(userLoanHistory.getType())
                .createdDate(userLoanHistory.getCreatedDate())
                .build();
    }

}
