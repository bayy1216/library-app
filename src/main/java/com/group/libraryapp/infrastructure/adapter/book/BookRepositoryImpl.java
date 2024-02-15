package com.group.libraryapp.infrastructure.adapter.book;

import com.group.libraryapp.domain.model.book.Book;
import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.domain.port.book.BookRepository;
import com.group.libraryapp.core.type.BookCategory;
import com.group.libraryapp.core.type.GetBookSortType;
import com.group.libraryapp.infrastructure.book.BookEntity;
import com.group.libraryapp.infrastructure.book.BookJpaRepository;
import com.group.libraryapp.infrastructure.book.BookQueryRepository;
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
