package com.group.libraryapp.mock;

import com.group.libraryapp.core.type.LoanType;
import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.domain.port.book.UserLoanHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FakeUserLoanHistoryRepository implements UserLoanHistoryRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<UserLoanHistory> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public List<UserLoanHistory> findByUserAndType(User user, LoanType loanType) {
        return data.stream()
                .filter(b -> b.getUser().equals(user) && b.getType().equals(loanType))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserLoanHistory> findByIdWithUserAndBook(Long loanId) {
        return data.stream()
                .filter(b -> b.getId().equals(loanId))
                .findFirst();
    }

    @Override
    public UserLoanHistory save(UserLoanHistory userLoanHistory) {
        if (userLoanHistory.getId() == null) {
            UserLoanHistory generated = UserLoanHistory.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .user(userLoanHistory.getUser())
                    .book(userLoanHistory.getBook())
                    .type(userLoanHistory.getType())
                    .createdDate(userLoanHistory.getCreatedDate())
                    .build();
            data.add(generated);
            return generated;
        } else {
            data.removeIf(b -> b.getId().equals(userLoanHistory.getId()));
            data.add(userLoanHistory);
            return userLoanHistory;
        }
    }

    @Override
    public Page<UserLoanHistory> getLoanHistory(Long userId, int page) {
        var pageRequest = PageRequest.of(page, 20);
        List<UserLoanHistory> content = new ArrayList<>(data);

        int start = (int)pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), content.size());
        return new PageImpl<>(content.subList(start, end), pageRequest, content.size());
    }
}