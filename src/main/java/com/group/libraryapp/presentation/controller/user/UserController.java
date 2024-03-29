package com.group.libraryapp.presentation.controller.user;

import com.group.libraryapp.presentation.interceptor.annotation.JwtFilterExclusion;
import com.group.libraryapp.presentation.interceptor.annotation.Login;
import com.group.libraryapp.core.jwt.JwtToken;
import com.group.libraryapp.core.jwt.UserAuth;
import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.domain.service.auth.AuthService;
import com.group.libraryapp.presentation.controller.auth.response.TokenResponse;
import com.group.libraryapp.presentation.controller.common.response.PagingResponse;
import com.group.libraryapp.presentation.controller.user.request.ChargeMoneyRequest;
import com.group.libraryapp.presentation.controller.user.request.CreateUserRequest;
import com.group.libraryapp.presentation.controller.user.response.UserBuyHistoryDto;
import com.group.libraryapp.presentation.controller.user.response.UserInfoResponse;
import com.group.libraryapp.presentation.controller.user.response.UserLoanHistoryDto;
import com.group.libraryapp.domain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("api/v1/user")
    public ResponseEntity<UserInfoResponse> getUserInfo(@Login UserAuth userAuth) {
        User user = userService.getUserById(userAuth.getId());
        UserInfoResponse response = UserInfoResponse.fromDomain(user);
        return ResponseEntity.ok(response);
    }

    @JwtFilterExclusion
    @PostMapping("api/v1/user")
    public ResponseEntity<TokenResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        Long id = userService.createUser(request.toDomain());
        JwtToken token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(TokenResponse.of(token));
    }

    @DeleteMapping("api/v1/user")
    public ResponseEntity<Object> deleteUser(@Login UserAuth userAuth) {
        userService.deleteUser(userAuth.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("api/v1/user/money")
    public ResponseEntity<Integer> chargeMoney(@Login UserAuth userAuth, @Valid @RequestBody ChargeMoneyRequest request) {
        Integer money = userService.chargeMoney(userAuth.getId(), request.getMoney());
        return ResponseEntity.ok().body(money);
    }

    @GetMapping("api/v1/user/book/loan")
    public ResponseEntity<PagingResponse<UserLoanHistoryDto>> pagingLoanHistory(
            @Login UserAuth userAuth,
            @RequestParam("page") int page
    ){
        Page<UserLoanHistory> userLoanHistories = userService.pagingLoanHistory(userAuth.getId(), page);
        List<UserLoanHistoryDto> dtos = userLoanHistories.getContent().stream()
                .map(UserLoanHistoryDto::fromDomain)
                .collect(Collectors.toList());
        PagingResponse<UserLoanHistoryDto> response = new PagingResponse<>(userLoanHistories.getTotalPages(), dtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("api/v1/user/book/buy")
    public ResponseEntity<PagingResponse<UserBuyHistoryDto>> pagingBuyHistory(
            @Login UserAuth userAuth,
            @RequestParam("page") int page
    ){
        Page<UserBuyHistory> userBuyHistories = userService.pagingBuyHistory(userAuth.getId(), page);
        List<UserBuyHistoryDto> dtos = userBuyHistories.getContent().stream()
                .map(UserBuyHistoryDto::fromDomain)
                .collect(Collectors.toList());
        PagingResponse<UserBuyHistoryDto> response = new PagingResponse<>(userBuyHistories.getTotalPages(), dtos);
        return ResponseEntity.ok(response);
    }


}
