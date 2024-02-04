package com.group.libraryapp.infrastructure.book;

import com.group.libraryapp.domain.model.book.Book;
import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.domain.port.book.BookRepository;
import com.group.libraryapp.domain.type.BookCategory;
import com.group.libraryapp.domain.type.GetBookSortType;
import com.group.libraryapp.infrastructure.book.buyhistory.UserBuyHistoryEntity;
import com.group.libraryapp.infrastructure.book.loanhistory.UserLoanHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final BookJpaRepository bookJpaRepository;
    private final BookQueryRepository bookQueryRepository;
    @Override
    public Optional<Book> findById(Long bookId) {
        return bookJpaRepository.findById(bookId).map(BookEntity::toDomain);
    }

    @Override
    public Page<UserBuyHistory> getBuyHistory(Long userId, int page) {
        Page<UserBuyHistoryEntity> userBuyHistoryEntities = bookQueryRepository.getBuyHistory(userId, page);
        return new PageImpl<>(
                userBuyHistoryEntities.getContent().stream().map(UserBuyHistoryEntity::toDomain).collect(Collectors.toList()),
                userBuyHistoryEntities.getPageable(),
                userBuyHistoryEntities.getTotalElements()
        );
    }

    @Override
    public Page<UserLoanHistory> getLoanHistory(Long userId, int page) {
        Page<UserLoanHistoryEntity> userLoanHistoryEntities = bookQueryRepository.getLoanHistory(userId, page);
        return new PageImpl<>(
                userLoanHistoryEntities.getContent().stream().map(UserLoanHistoryEntity::toDomain).collect(Collectors.toList()),
                userLoanHistoryEntities.getPageable(),
                userLoanHistoryEntities.getTotalElements()
        );
    }

    @Override
    public void delete(Book book) {
        bookJpaRepository.delete(BookEntity.fromDomain(book));
    }

    @Override
    public Book save(Book book) {
        return bookJpaRepository.save(BookEntity.fromDomain(book)).toDomain();
    }

    @Override
    public Page<Book> getBookPage(int page, String name, String writer, BookCategory category, GetBookSortType sort) {
        Page<BookEntity> bookEntities = bookQueryRepository.getBookPage(page, name, writer, category, sort);
        return new PageImpl<>(
                bookEntities.getContent().stream().map(BookEntity::toDomain).collect(Collectors.toList()),
                bookEntities.getPageable(),
                bookEntities.getTotalElements()
        );
    }
}
