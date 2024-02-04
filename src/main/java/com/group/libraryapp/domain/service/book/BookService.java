package com.group.libraryapp.domain.service.book;

import com.group.libraryapp.domain.model.book.Book;
import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.domain.port.book.BookRepository;
import com.group.libraryapp.domain.port.book.buyhistory.UserBuyHistoryRepository;
import com.group.libraryapp.domain.port.book.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.domain.port.user.UserRepository;
import com.group.libraryapp.presentation.dto.book.request.CreateBookRequest;
import com.group.libraryapp.presentation.dto.book.request.UpdateBookStockRequest;
import com.group.libraryapp.domain.type.BookCategory;
import com.group.libraryapp.domain.type.GetBookSortType;
import com.group.libraryapp.domain.type.LoanType;
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


    public void loanBook(Long bookId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 책입니다.")
        );
        List<UserLoanHistory> userCurrentLoanHistories = userLoanHistoryRepository.findByUserAndType(user, LoanType.LOANED);
        UserLoanHistory userLoanHistory = user.loanBook(book, userCurrentLoanHistories);
        userLoanHistoryRepository.save(userLoanHistory);
    }

    public void returnBook(Long loanId, Long userId) {
        UserLoanHistory userLoanHistory = userLoanHistoryRepository.findByIdWithUserAndBook(loanId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 대여 기록입니다.")
        );
        User user = userLoanHistory.getUser();
        if(!user.getId().equals(userId)) throw new IllegalArgumentException("해당 유저의 대여 기록이 아닙니다.");

        userLoanHistory = userLoanHistory.returnBook();
        userLoanHistoryRepository.save(userLoanHistory);
    }

    public void buyBook(Long bookId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 책입니다.")
        );
        user = user.buyBook(book);
        UserBuyHistory userBuyHistory = UserBuyHistory.builder()
                .user(user)
                .book(book)
                .build();
        userRepository.save(user);
        userBuyHistoryRepository.save(userBuyHistory);
    }

    public Long createBook(CreateBookRequest request) {
        Book book = Book.builder()
                .name(request.getName())
                .writer(request.getWriter())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .stock(0)
                .publishedDate(request.getPublishedDate())
                .build();
        bookRepository.save(book);
        return book.getId();
    }

    public void updateBook(CreateBookRequest request, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 책입니다.")
        );
        book = book.updateBook(request.getName(), request.getWriter(), request.getDescription(), request.getCategory(), request.getPrice(), request.getPublishedDate());
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 책입니다.")
        );
        bookRepository.delete(book);
    }

    public void updateBookStock(Long bookId, UpdateBookStockRequest request) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 책입니다.")
        );
        book = book.addStock(request.getStock());
        bookRepository.save(book);
    }
}
