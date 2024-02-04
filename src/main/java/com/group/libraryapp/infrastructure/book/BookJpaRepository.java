package com.group.libraryapp.infrastructure.book;

import com.group.libraryapp.infrastructure.book.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long>{
    Optional<BookEntity> findByName(String name);
}
