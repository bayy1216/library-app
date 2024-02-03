package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.book.BookQueryRepository;
import com.group.libraryapp.domain.book.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.common.response.PagingResponse;
import com.group.libraryapp.dto.user.request.CreateUserRequest;
import com.group.libraryapp.dto.user.response.UserBuyHistoryDto;
import com.group.libraryapp.dto.user.response.UserInfoResponse;
import com.group.libraryapp.dto.user.response.UserLoanHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookQueryRepository bookQueryRepository;

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        return UserInfoResponse.fromDomain(user);
    }

    public Long createUser(CreateUserRequest request) {
        User user = User.builder()
                .name(request.getName())
                .age(request.getAge())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        userRepository.save(user);
        return user.getId();
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        userRepository.delete(user);
    }

    public void chargeMoney(Long userId, Integer money) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        user.chargeMoney(money);
    }
    @Transactional(readOnly = true)
    public PagingResponse<UserLoanHistoryDto> pagingLoanHistory(Long userId, int page) {
        Page<UserLoanHistory> loanHistoryPage = bookQueryRepository.getLoanHistory(userId, page);
        List<UserLoanHistoryDto> userLoanHistoryDtos = loanHistoryPage.getContent().stream().map(
                UserLoanHistoryDto::fromDomain
        ).collect(Collectors.toList());
        return PagingResponse.<UserLoanHistoryDto>builder().totalPage(loanHistoryPage.getTotalPages())
                .data(userLoanHistoryDtos).build();
    }
    @Transactional(readOnly = true)
    public PagingResponse<UserBuyHistoryDto> pagingBuyHistory(Long userId, int page) {
        throw new UnsupportedOperationException();
    }
}
