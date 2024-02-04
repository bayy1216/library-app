package com.group.libraryapp.infrastructure.book.buyhistory;

import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.domain.port.book.buyhistory.UserBuyHistoryRepository;
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
