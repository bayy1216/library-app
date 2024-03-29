package com.group.libraryapp.infrastructure.adapter.book;

import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.domain.port.book.UserLoanHistoryRepository;
import com.group.libraryapp.core.type.LoanType;
import com.group.libraryapp.infrastructure.book.BookQueryRepository;
import com.group.libraryapp.infrastructure.book.loanhistory.UserLoanHistoryEntity;
import com.group.libraryapp.infrastructure.book.loanhistory.UserLoanHistoryJpaRepository;
import com.group.libraryapp.infrastructure.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserLoanHistoryRepositoryImpl implements UserLoanHistoryRepository {
    private final UserLoanHistoryJpaRepository userLoanHistoryJpaRepository;
    private final BookQueryRepository bookQueryRepository;

    @Override
    public List<UserLoanHistory> findByUserAndType(User user, LoanType loanType) {
        return userLoanHistoryJpaRepository
                .findByUserAndType(UserEntity.fromDomain(user), loanType)
                .stream().map(UserLoanHistoryEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<UserLoanHistory> findByIdWithUserAndBook(Long loanId) {
        return userLoanHistoryJpaRepository.findByIdWithUserAndBook(loanId).map(UserLoanHistoryEntity::toDomain);
    }

    @Override
    public UserLoanHistory save(UserLoanHistory userLoanHistory) {
        return userLoanHistoryJpaRepository.save(UserLoanHistoryEntity.fromDomain(userLoanHistory)).toDomain();
    }

    @Override
    public Page<UserLoanHistory> getLoanHistory(Long userId, int page) {
        Page<UserLoanHistoryEntity> userLoanHistoryEntities = bookQueryRepository.getLoanHistory(userId, page);
        return new PageImpl<>(
                userLoanHistoryEntities.getContent().stream().map(UserLoanHistoryEntity::toDomain).collect(Collectors.toList()),
                userLoanHistoryEntities.getPageable(),
                userLoanHistoryEntities.getTotalElements()
        );
    }
}
