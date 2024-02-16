package com.group.libraryapp.presentation.controller.book;

import com.group.libraryapp.core.config.SecurityConfig;
import com.group.libraryapp.core.jwt.JwtUtils;
import com.group.libraryapp.domain.model.book.Book;
import com.group.libraryapp.domain.service.book.BookService;
import com.group.libraryapp.presentation.config.WebConfig;
import com.group.libraryapp.presentation.interceptor.JwtInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@AutoConfigureRestDocs
@WebMvcTest(value = BookController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebConfig.class, JwtInterceptor.class}))
@Import(SecurityConfig.class)
class BookControllerTest {
    @Autowired private MockMvc mockMvc;

    @MockBean private BookService bookService;



    @Test
    @DisplayName("도서 목록 조회")
    void getBookList() throws Exception {
        int page = 0;
        List<Book> books = new ArrayList<>();
        books.add(Book.builder().id(1L).name("book1").writer("writer1").price(1000).stock(10).publishedDate(LocalDate.now()).build());
        books.add(Book.builder().id(2L).name("book2").writer("writer2").price(2000).stock(20).publishedDate(LocalDate.now()).build());
        books.add(Book.builder().id(3L).name("book3").writer("writer3").price(3000).stock(30).publishedDate(LocalDate.now()).build());
        books.add(Book.builder().id(4L).name("book4").writer("writer4").price(4000).stock(40).publishedDate(LocalDate.now()).build());
        books.add(Book.builder().id(5L).name("book5").writer("writer5").price(5000).stock(50).publishedDate(LocalDate.now()).build());
        Page<Book> booksPage = new PageImpl<>(books,PageRequest.of(0,20),5);
        given(bookService.getBookList(page, null, null, null, null)).willReturn(
                booksPage
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book?page=0"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcRestDocumentation.document("book/getBookList",
                        Preprocessors.preprocessRequest(prettyPrint()),
                        Preprocessors.preprocessResponse(prettyPrint())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void loanBook() {
    }

    @Test
    void returnBook() {
    }

    @Test
    void buyBook() {
    }

    @Test
    void createBook() {
    }

    @Test
    void updateBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void updateBookStock() {
    }
}