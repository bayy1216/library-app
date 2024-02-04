package com.group.libraryapp.infrastructure.adapter.book;

import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.domain.port.book.UserBuyHistoryRepository;
import com.group.libraryapp.infrastructure.book.buyhistory.UserBuyHistoryEntity;
import com.group.libraryapp.infrastructure.book.buyhistory.UserBuyHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserBuyHistoryRepositoryImpl implements UserBuyHistoryRepository {
    private final UserBuyHistoryJpaRepository userBuyHistoryJpaRepository;
    @Override
    public UserBuyHistory save(UserBuyHistory userBuyHistory) {
        return userBuyHistoryJpaRepository.save(UserBuyHistoryEntity.fromDomain(userBuyHistory)).toDomain();
    }
}
