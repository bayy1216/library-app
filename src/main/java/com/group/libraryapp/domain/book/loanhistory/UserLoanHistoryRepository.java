package com.group.libraryapp.domain.book.loanhistory;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.type.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {
    List<UserLoanHistory> findByUserAndType(User user, LoanType type);

    /**
     * book과 user를 fetch join하여 조회한다.
     */
    @Query("select ulh from UserLoanHistory ulh join fetch ulh.book join fetch ulh.user where ulh.id = :id")
    Optional<UserLoanHistory> findByIdWithUserAndBook(Long id);
}
