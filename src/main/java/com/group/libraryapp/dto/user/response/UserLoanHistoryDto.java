package com.group.libraryapp.dto.user.response;

import com.group.libraryapp.domain.book.loanhistory.UserLoanHistory;
import com.group.libraryapp.dto.book.response.BookSummaryDto;
import com.group.libraryapp.type.LoanType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoanHistoryDto {
    private BookSummaryDto book;
    private Long loanId;
    private String createdDate;
    private boolean isReturned;

    public static UserLoanHistoryDto fromDomain(UserLoanHistory userLoanHistory) {
        return UserLoanHistoryDto.builder()
                .book(BookSummaryDto.fromDomain(userLoanHistory.getBook()))
                .loanId(userLoanHistory.getId())
                .createdDate(userLoanHistory.getCreatedDate().toString())
                .isReturned(userLoanHistory.getType() == LoanType.RETURNED)
                .build();
    }
}
