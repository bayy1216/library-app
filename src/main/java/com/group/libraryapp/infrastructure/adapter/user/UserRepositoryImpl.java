package com.group.libraryapp.infrastructure.adapter.user;

import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.domain.port.user.UserRepository;
import com.group.libraryapp.infrastructure.user.UserEntity;
import com.group.libraryapp.infrastructure.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId).map(UserEntity::toDomain);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.fromDomain(user)).toDomain();
    }

    @Override
    public void delete(User user) {
        userJpaRepository.delete(UserEntity.fromDomain(user));
    }
}
