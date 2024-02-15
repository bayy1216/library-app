package com.group.libraryapp.domain.service.book;

import com.group.libraryapp.core.exception.ResourceNotFoundException;
import com.group.libraryapp.core.type.BookCategory;
import com.group.libraryapp.domain.model.book.Book;
import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.domain.port.book.BookRepository;
import com.group.libraryapp.domain.port.book.UserBuyHistoryRepository;
import com.group.libraryapp.domain.port.book.UserLoanHistoryRepository;
import com.group.libraryapp.domain.port.user.UserRepository;
import com.group.libraryapp.mock.FakeBookRepository;
import com.group.libraryapp.mock.FakeUserBuyHistoryRepository;
import com.group.libraryapp.mock.FakeUserLoanHistoryRepository;
import com.group.libraryapp.mock.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class BookServiceTest {
    private BookService bookService;

    @BeforeEach
    void init(){
        UserRepository userRepository = new FakeUserRepository();
        UserLoanHistoryRepository userLoanHistoryRepository = new FakeUserLoanHistoryRepository();
        UserBuyHistoryRepository userBuyHistoryRepository = new FakeUserBuyHistoryRepository();
        BookRepository bookRepository = new FakeBookRepository();
        bookService = BookService.builder()
                .bookRepository(bookRepository).userRepository(userRepository)
                .userLoanHistoryRepository(userLoanHistoryRepository).userBuyHistoryRepository(userBuyHistoryRepository)
                .build();

        User user1 = User.builder()
                .id(1L)
                .name("user1")
                .age(20)
                .money(10000)
                .email("test@a.c")
                .password("1234")
                .build();
        User user2 = User.builder()
                .id(2L)
                .name("user2")
                .age(20)
                .money(0)
                .email("test@a.c")
                .password("1234")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        Book book1 = Book.builder()
                .id(1L)
                .name("book1")
                .writer("writer1")
                .price(1000)
                .stock(10)
                .description("description1")
                .category(BookCategory.FANTASY)
                .publishedDate(LocalDate.of(2023,1,1))
                .build();
        Book book2 = Book.builder()
                .id(2L)
                .name("book2")
                .writer("writer2")
                .price(1000)
                .stock(0)
                .description("description2")
                .category(BookCategory.FANTASY)
                .publishedDate(LocalDate.of(2023,1,1))
                .build();
        bookRepository.save(book1);
        bookRepository.save(book2);
    }


    @Test
    void loanBook이_적상적으로_작동한다() {
        var user1Id = 1L;
        var book1Id = 1L;
        Long loanHistory = bookService.loanBook(book1Id, user1Id);
        assertNotNull(loanHistory);
        assertThrows(ResourceNotFoundException.class, () -> bookService.loanBook(book1Id, 999L));
        assertThrows(ResourceNotFoundException.class, () -> bookService.loanBook(999L, user1Id));
    }

    @Test
    void 재고가없는책은_못빌린다(){
        var noStockBookId = 2L;
        var user1Id = 1L;
        assertThrows(IllegalArgumentException.class, () -> bookService.loanBook(noStockBookId, user1Id));
    }

    @Test
    void returnBook이_적상적으로_작동한다() {
        var user1Id = 1L;
        var book1Id = 1L;
        Long loanHistory = bookService.loanBook(book1Id, user1Id);
        bookService.returnBook(loanHistory, user1Id);
    }

    @Test
    void 대여한_책만_반납할수있다(){
        var user1Id = 1L;
        var book1Id = 1L;
        Long loanHistory = bookService.loanBook(book1Id, user1Id);
        var user2Id = 2L;
        Long loanHistory2 = bookService.loanBook(book1Id, user2Id);
        assertThrows(IllegalArgumentException.class, () -> bookService.returnBook(loanHistory2, user1Id));
    }

    @Test
    void 다른이의_책은_반납할수없다(){
        var user1Id = 1L;
        var book1Id = 1L;
        Long loanHistory = bookService.loanBook(book1Id, user1Id);
        assertThrows(IllegalArgumentException.class, () -> bookService.returnBook(loanHistory, 999L));
    }


    @Test
    void buyBook이_적상적으로_작동한다() {
        var user1Id = 1L;
        var book1Id = 1L;
        Long buyHistory = bookService.buyBook(book1Id, user1Id);
        assertNotNull(buyHistory);
    }


    @Test
    void 재고가없는책은_못산다(){
        var noStockBookId = 2L;
        var user1Id = 1L;
        assertThrows(IllegalArgumentException.class, () -> bookService.buyBook(noStockBookId, user1Id));
    }
    @Test
    void 돈이없으면_책을_못산다(){
        var user2Id = 2L;
        var book1Id = 1L;
        assertThrows(IllegalArgumentException.class, () -> bookService.buyBook(book1Id, user2Id));
    }


}