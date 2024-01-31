package com.group.libraryapp.dto.user.response;

import com.group.libraryapp.dto.book.response.BookSummaryDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoanHistoryDto {
    private BookSummaryDto book;
    private Long loanId;
    private String createdDate;
    private boolean isReturned;
}
