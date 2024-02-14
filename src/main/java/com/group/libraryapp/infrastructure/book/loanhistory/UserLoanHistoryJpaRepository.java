package com.group.libraryapp.infrastructure.book.loanhistory;

import com.group.libraryapp.infrastructure.user.UserEntity;
import com.group.libraryapp.core.type.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserLoanHistoryJpaRepository extends JpaRepository<UserLoanHistoryEntity, Long> {
    List<UserLoanHistoryEntity> findByUserAndType(UserEntity user, LoanType type);

    /**
     * book과 user를 fetch join하여 조회한다.
     */
    @Query("select ulh from UserLoanHistoryEntity ulh join fetch ulh.book join fetch ulh.user where ulh.id = :id")
    Optional<UserLoanHistoryEntity> findByIdWithUserAndBook(@Param("id") Long id);
}
