package com.group.libraryapp.domain.port.book;

import com.group.libraryapp.domain.model.book.Book;
import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.domain.type.BookCategory;
import com.group.libraryapp.domain.type.GetBookSortType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository {
    Optional<Book> findById(Long bookId);

    Page<UserBuyHistory> getBuyHistory(Long userId, int page);

    Page<UserLoanHistory> getLoanHistory(Long userId, int page);

    void delete(Book book);

    Book save(Book book);

    Page<Book> getBookPage(int page, String name, String writer, BookCategory category, GetBookSortType sort);
}
