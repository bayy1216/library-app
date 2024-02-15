package com.group.libraryapp.domain.port.book;

import com.group.libraryapp.domain.model.book.UserBuyHistory;
import org.springframework.data.domain.Page;

public interface UserBuyHistoryRepository {
    UserBuyHistory save(UserBuyHistory userBuyHistory);


    Page<UserBuyHistory> getBuyHistory(Long userId, int page);
}
