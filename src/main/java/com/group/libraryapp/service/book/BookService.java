package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.book.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.book.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.book.request.CreateBookRequest;
import com.group.libraryapp.dto.book.request.UpdateBookStockRequest;
import com.group.libraryapp.dto.book.response.BookInfoDto;
import com.group.libraryapp.dto.common.response.PagingResponse;
import com.group.libraryapp.type.GetBookSortType;
import com.group.libraryapp.type.LoanType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public PagingResponse<BookInfoDto> getBookList(int page, String name, String writer, String category, GetBookSortType sort) {
        throw new UnsupportedOperationException();
    }


    public void loanBook(Long bookId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 책입니다.")
        );
        List<UserLoanHistory> userLoanHistories = userLoanHistoryRepository.findByUserAndType(user, LoanType.LOANED);
        if(userLoanHistories.size() >= 10) throw new IllegalArgumentException("대여 가능한 책의 수를 초과하였습니다.");
        UserLoanHistory userLoanHistory = UserLoanHistory.builder()
                .user(user)
                .book(book)
                .type(LoanType.LOANED)
                .build();
        userLoanHistoryRepository.save(userLoanHistory);
    }

    public void returnBook(Long loanId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        UserLoanHistory userLoanHistory = userLoanHistoryRepository.findById(loanId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 대여 기록입니다.")
        );
        if(userLoanHistory.getType() == LoanType.RETURNED) throw new IllegalArgumentException("이미 반납된 책입니다.");
        user.returnBook(userLoanHistory);
    }

    public void buyBook(Long bookId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 책입니다.")
        );
        user.buyBook(book);
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

        book.updateBook(request.getName(), request.getWriter(), request.getDescription(), request.getCategory(), request.getPrice(), request.getPublishedDate());
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
        book.updateStock(request.getStock());
    }
}
