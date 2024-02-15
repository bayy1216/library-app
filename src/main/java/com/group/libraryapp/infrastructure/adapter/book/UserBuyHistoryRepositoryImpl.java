package com.group.libraryapp.infrastructure.adapter.book;

import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.domain.port.book.UserBuyHistoryRepository;
import com.group.libraryapp.infrastructure.book.BookQueryRepository;
import com.group.libraryapp.infrastructure.book.buyhistory.UserBuyHistoryEntity;
import com.group.libraryapp.infrastructure.book.buyhistory.UserBuyHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserBuyHistoryRepositoryImpl implements UserBuyHistoryRepository {
    private final UserBuyHistoryJpaRepository userBuyHistoryJpaRepository;
    private final BookQueryRepository bookQueryRepository;
    @Override
    public UserBuyHistory save(UserBuyHistory userBuyHistory) {
        return userBuyHistoryJpaRepository.save(UserBuyHistoryEntity.fromDomain(userBuyHistory)).toDomain();
    }

    @Override
    public Page<UserBuyHistory> getBuyHistory(Long userId, int page) {
        Page<UserBuyHistoryEntity> userBuyHistoryEntities = bookQueryRepository.getBuyHistory(userId, page);
        return new PageImpl<>(
                userBuyHistoryEntities.getContent().stream().map(UserBuyHistoryEntity::toDomain).collect(Collectors.toList()),
                userBuyHistoryEntities.getPageable(),
                userBuyHistoryEntities.getTotalElements()
        );
    }
}
