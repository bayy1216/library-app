package com.group.libraryapp.presentation.controller.user.response;

import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.presentation.controller.book.response.BookSummaryDto;
import com.group.libraryapp.core.type.LoanType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoanHistoryDto {
    private BookSummaryDto book;
    private Long loanId;
    private String createdDate;
    private boolean isReturned;

    public static UserLoanHistoryDto fromDomain(UserLoanHistory userLoanHistory){
        return UserLoanHistoryDto.builder()
                .book(BookSummaryDto.fromDomain(userLoanHistory.getBook()))
                .loanId(userLoanHistory.getId())
                .createdDate(userLoanHistory.getCreatedDate().toString())
                .isReturned(userLoanHistory.getType() == LoanType.RETURNED)
                .build();
    }
}
