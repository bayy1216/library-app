package com.group.libraryapp.dto.user.response;

import com.group.libraryapp.domain.book.buyhistory.UserBuyHistory;
import com.group.libraryapp.dto.book.response.BookSummaryDto;
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
