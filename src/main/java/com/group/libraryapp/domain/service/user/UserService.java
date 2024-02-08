package com.group.libraryapp.domain.service.user;

import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.domain.model.user.UserCreate;
import com.group.libraryapp.domain.port.book.BookRepository;
import com.group.libraryapp.domain.port.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
    }

    @Transactional
    public Long createUser(UserCreate userCreate) {
        User user = User.from(userCreate);
        user = userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        userRepository.delete(user);
    }

    @Transactional
    public Integer chargeMoney(Long userId, Integer money) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        user = user.chargeMoney(money);
        userRepository.save(user);
        return user.getMoney();
    }
    @Transactional
    public Page<UserLoanHistory> pagingLoanHistory(Long userId, int page) {
        return bookRepository.getLoanHistory(userId, page);
    }

    @Transactional
    public Page<UserBuyHistory> pagingBuyHistory(Long userId, int page) {
        return bookRepository.getBuyHistory(userId, page);
    }
}
