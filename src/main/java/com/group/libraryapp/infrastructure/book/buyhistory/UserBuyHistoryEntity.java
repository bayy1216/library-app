package com.group.libraryapp.infrastructure.book.buyhistory;

import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.infrastructure.book.BookEntity;
import com.group.libraryapp.infrastructure.user.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserBuyHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @JoinColumn(name = "book_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private BookEntity book;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Builder
    public UserBuyHistoryEntity(Long id, UserEntity user, BookEntity book) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.createdDate = LocalDate.now();
    }

    public static UserBuyHistoryEntity fromDomain(UserBuyHistory userBuyHistory) {
        return UserBuyHistoryEntity.builder()
                .id(userBuyHistory.getId())
                .user(UserEntity.fromDomain(userBuyHistory.getUser()))
                .book(BookEntity.fromDomain(userBuyHistory.getBook()))
                .build();
    }

    public UserBuyHistory toDomain() {
        return UserBuyHistory.builder()
                .id(id)
                .user(user.toDomain())
                .book(book.toDomain())
                .createdDate(createdDate)
                .build();
    }
}
