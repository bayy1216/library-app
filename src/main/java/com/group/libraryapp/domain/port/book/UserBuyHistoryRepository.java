package com.group.libraryapp.domain.port.book;

import com.group.libraryapp.domain.model.book.UserBuyHistory;

public interface UserBuyHistoryRepository {
    UserBuyHistory save(UserBuyHistory userBuyHistory);
}
