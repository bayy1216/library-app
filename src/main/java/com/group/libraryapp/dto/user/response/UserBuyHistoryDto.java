package com.group.libraryapp.dto.user.response;

import com.group.libraryapp.dto.book.response.BookSummaryDto;

public class UserBuyHistoryDto {
    private BookSummaryDto book;
    private Long buyId;
    private String createdDate;
}
