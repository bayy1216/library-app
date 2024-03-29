package com.group.libraryapp.domain.service.user;

import com.group.libraryapp.core.exception.ResourceNotFoundException;
import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.domain.model.user.UserCreate;
import com.group.libraryapp.domain.port.book.BookRepository;
import com.group.libraryapp.domain.port.book.UserBuyHistoryRepository;
import com.group.libraryapp.domain.port.book.UserLoanHistoryRepository;
import com.group.libraryapp.domain.port.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final UserBuyHistoryRepository userBuyHistoryRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", userId)
        );
    }

    @Transactional
    public Long createUser(UserCreate userCreate) {
        String bCryptPassword = bCryptPasswordEncoder.encode(userCreate.getPassword());
        userCreate = userCreate.changePassword(bCryptPassword);
        userRepository.findByEmail(userCreate.getEmail()).ifPresent(
                user -> {
                    throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
                }
        );
        User user = User.from(userCreate);
        user = userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", userId)
        );
        userRepository.delete(user);
    }

    @Transactional
    public Integer chargeMoney(Long userId, Integer money) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", userId)
        );
        user = user.chargeMoney(money);
        userRepository.save(user);
        return user.getMoney();
    }
    @Transactional
    public Page<UserLoanHistory> pagingLoanHistory(Long userId, int page) {
        return userLoanHistoryRepository.getLoanHistory(userId, page);
    }

    @Transactional
    public Page<UserBuyHistory> pagingBuyHistory(Long userId, int page) {
        return userBuyHistoryRepository.getBuyHistory(userId, page);
    }
}
