package com.group.libraryapp.controller.book;

import com.group.libraryapp.dto.book.request.CreateBookRequest;
import com.group.libraryapp.dto.book.request.UpdateBookStockRequest;
import com.group.libraryapp.dto.book.response.BookInfoDto;
import com.group.libraryapp.dto.common.response.PagingResponse;
import com.group.libraryapp.service.book.BookService;
import com.group.libraryapp.type.BookCategory;
import com.group.libraryapp.type.GetBookSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        PagingResponse<BookInfoDto> response = bookService.getBookList(page, name, writer, category, sort);
        return ResponseEntity.ok(response);
    }

    @PostMapping("api/v1/book/{bookId}/loan")
    public ResponseEntity<Object> loanBook(@PathVariable Long bookId, @RequestHeader("Authorization") Long userId) {
        bookService.loanBook(bookId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("api/v1/book/return/{loanId}")
    public ResponseEntity<Object> returnBook(@PathVariable Long loanId, @RequestHeader("Authorization") Long userId) {
        bookService.returnBook(loanId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("api/v1/book/{bookId}/buy")
    public ResponseEntity<Object> buyBook(@PathVariable Long bookId, @RequestHeader("Authorization") Long userId) {
        bookService.buyBook(bookId, userId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("api/v1/book")
    public ResponseEntity<Long> createBook(@RequestBody CreateBookRequest request) {
        Long id = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("api/v1/book/{bookId}")
    public ResponseEntity<Object> updateBook(@RequestBody CreateBookRequest request, @PathVariable Long bookId) {
        bookService.updateBook(request, bookId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("api/v1/book/{bookId}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("api/v1/book/{bookId}/stock")
    public ResponseEntity<Object> updateBookStock(@PathVariable Long bookId, @RequestBody UpdateBookStockRequest request) {
        bookService.updateBookStock(bookId, request);
        return ResponseEntity.ok().build();
    }

}
