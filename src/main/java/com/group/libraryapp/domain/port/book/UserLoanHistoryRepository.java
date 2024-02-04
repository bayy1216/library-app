package com.group.libraryapp.domain.port.book;

import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.domain.type.LoanType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserLoanHistoryRepository {
    List<UserLoanHistory> findByUserAndType(User user, LoanType loanType);

    Optional<UserLoanHistory> findByIdWithUserAndBook(Long loanId);

    UserLoanHistory save(UserLoanHistory userLoanHistory);
}
