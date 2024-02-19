package com.group.libraryapp.presentation.controller.user.response;

import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.presentation.controller.book.response.BookSummaryDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserBuyHistoryDto {
    private BookSummaryDto book;
    private Long buyId;
    private String createdDate;

    public static UserBuyHistoryDto fromDomain(UserBuyHistory userBuyHistory) {
        return UserBuyHistoryDto.builder()
                .book(BookSummaryDto.fromDomain(userBuyHistory.getBook()))
                .buyId(userBuyHistory.getId())
                .createdDate(userBuyHistory.getCreatedDate().toString())
                .build();
    }
}
