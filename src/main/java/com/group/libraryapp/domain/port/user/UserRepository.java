package com.group.libraryapp.domain.port.user;

import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.infrastructure.user.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long userId);

    User save(User user);

    void delete(User user);
}
