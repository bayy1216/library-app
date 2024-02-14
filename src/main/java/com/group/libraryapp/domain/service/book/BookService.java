package com.group.libraryapp.domain.service.book;

import com.group.libraryapp.core.exception.ResourceNotFoundException;
import com.group.libraryapp.domain.model.book.*;
import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.domain.port.book.BookRepository;
import com.group.libraryapp.domain.port.book.UserBuyHistoryRepository;
import com.group.libraryapp.domain.port.book.UserLoanHistoryRepository;
import com.group.libraryapp.domain.port.user.UserRepository;
import com.group.libraryapp.core.type.BookCategory;
import com.group.libraryapp.core.type.GetBookSortType;
import com.group.libraryapp.core.type.LoanType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserBuyHistoryRepository userBuyHistoryRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<Book> getBookList(int page, String name, String writer, BookCategory category, GetBookSortType sort) {
        return bookRepository.getBookPage(page, name, writer, category, sort);
    }


    public Long loanBook(Long bookId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", userId)
        );
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book", bookId)
        );
        List<UserLoanHistory> userCurrentLoanHistories = userLoanHistoryRepository.findByUserAndType(user, LoanType.LOANED);
        UserLoanHistory userLoanHistory = user.loanBook(book, userCurrentLoanHistories);
        book = userLoanHistory.getBook();

        bookRepository.save(book);
        userLoanHistoryRepository.save(userLoanHistory);
        return userLoanHistory.getId();
    }

    public void returnBook(Long loanId, Long userId) {
        UserLoanHistory userLoanHistory = userLoanHistoryRepository.findByIdWithUserAndBook(loanId).orElseThrow(
                () -> new ResourceNotFoundException("UserLoanHistory", loanId)
        );
        User user = userLoanHistory.getUser();
        if(!user.getId().equals(userId)) throw new IllegalArgumentException("해당 유저의 대여 기록이 아닙니다.");

        userLoanHistory = user.returnBook(userLoanHistory);
        Book book = userLoanHistory.getBook();

        bookRepository.save(book);
        userLoanHistoryRepository.save(userLoanHistory);
    }

    public Long buyBook(Long bookId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", userId)
        );
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book", bookId)
        );
        UserBuyHistory userBuyHistory = user.buyBook(book);
        book = userBuyHistory.getBook();
        user = userBuyHistory.getUser();

        userRepository.save(user);
        bookRepository.save(book);
        userBuyHistoryRepository.save(userBuyHistory);
        return userBuyHistory.getId();
    }

    public Long createBook(BookCreate bookCreate) {
        Book book = Book.from(bookCreate);
        book = bookRepository.save(book);
        return book.getId();
    }

    public void updateBook(BookUpdate bookUpdate, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book", bookId)
        );
        book = book.update(bookUpdate);
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book", bookId)
        );
        bookRepository.delete(book);
    }

    public Integer updateBookStock(Long bookId, Integer stock) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book", bookId)
        );
        book = book.updateStock(stock);
        bookRepository.save(book);
        return book.getStock();
    }
}
