package com.group.libraryapp.presentation.controller.book;

import com.group.libraryapp.presentation.interceptor.annotation.Admin;
import com.group.libraryapp.presentation.interceptor.annotation.Login;
import com.group.libraryapp.core.jwt.UserAuth;
import com.group.libraryapp.domain.model.book.Book;
import com.group.libraryapp.presentation.controller.book.request.CreateBookRequest;
import com.group.libraryapp.presentation.controller.book.request.UpdateBookStockRequest;
import com.group.libraryapp.presentation.controller.book.response.BookInfoDto;
import com.group.libraryapp.presentation.controller.common.response.PagingResponse;
import com.group.libraryapp.domain.service.book.BookService;
import com.group.libraryapp.core.type.BookCategory;
import com.group.libraryapp.core.type.GetBookSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("api/v1/book")
    public ResponseEntity<PagingResponse<BookInfoDto>> getBookList(
            @RequestParam("page") int page,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "writer", required = false) String writer,
            @RequestParam(value = "category", required = false) BookCategory category,
            @RequestParam(value = "sort", required = false) GetBookSortType sort
    ){
        Page<Book> books = bookService.getBookList(page, name, writer, category, sort);
        List<BookInfoDto> dtos = books.getContent().stream()
                .map(BookInfoDto::fromDomain)
                .collect(Collectors.toList());
        PagingResponse<BookInfoDto> response = new PagingResponse<>(books.getTotalPages(), dtos);
        return ResponseEntity.ok(response);
    }

    @PostMapping("api/v1/book/{bookId}/loan")
    public ResponseEntity<Long> loanBook(@PathVariable Long bookId, @Login UserAuth userAuth) {
        Long id = bookService.loanBook(bookId, userAuth.getId());
        return ResponseEntity.ok().body(id);
    }

    @PostMapping("api/v1/book/return/{loanId}")
    public ResponseEntity<Object> returnBook(@PathVariable Long loanId, @Login UserAuth userAuth) {
        bookService.returnBook(loanId, userAuth.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("api/v1/book/{bookId}/buy")
    public ResponseEntity<Long> buyBook(@PathVariable Long bookId, @Login UserAuth userAuth) {
        Long id = bookService.buyBook(bookId, userAuth.getId());
        return ResponseEntity.ok().body(id);
    }

    @Admin
    @PostMapping("api/v1/book")
    public ResponseEntity<Long> createBook(@Valid @RequestBody CreateBookRequest request) {
        Long id = bookService.createBook(request.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
    @Admin
    @PutMapping("api/v1/book/{bookId}")
    public ResponseEntity<Object> updateBook(@Valid @RequestBody CreateBookRequest request, @PathVariable Long bookId) {
        bookService.updateBook(request.toUpdateDomain(), bookId);
        return ResponseEntity.ok().build();
    }

    @Admin
    @DeleteMapping("api/v1/book/{bookId}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }

    @Admin
    @PatchMapping("api/v1/book/{bookId}/stock")
    public ResponseEntity<Integer> updateBookStock(@PathVariable Long bookId, @Valid @RequestBody UpdateBookStockRequest request) {
        Integer totalStock = bookService.updateBookStock(bookId, request.getStock());
        return ResponseEntity.ok().body(totalStock);
    }

}
