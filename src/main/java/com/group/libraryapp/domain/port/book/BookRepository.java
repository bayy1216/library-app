package com.group.libraryapp.domain.port.book;

import com.group.libraryapp.domain.model.book.Book;
import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.core.type.BookCategory;
import com.group.libraryapp.core.type.GetBookSortType;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(Long bookId);

    Page<UserBuyHistory> getBuyHistory(Long userId, int page);

    Page<UserLoanHistory> getLoanHistory(Long userId, int page);

    void delete(Book book);

    Book save(Book book);

    Page<Book> getBookPage(int page, String name, String writer, BookCategory category, GetBookSortType sort);
}
