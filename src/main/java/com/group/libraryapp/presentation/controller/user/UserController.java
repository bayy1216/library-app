package com.group.libraryapp.presentation.controller.user;

import com.group.libraryapp.core.interceptor.JwtFilterExclusion;
import com.group.libraryapp.core.interceptor.Login;
import com.group.libraryapp.core.jwt.UserAuth;
import com.group.libraryapp.domain.model.book.UserBuyHistory;
import com.group.libraryapp.domain.model.book.UserLoanHistory;
import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.presentation.dto.response.PagingResponse;
import com.group.libraryapp.presentation.dto.user.request.ChargeMoneyRequest;
import com.group.libraryapp.presentation.dto.user.request.CreateUserRequest;
import com.group.libraryapp.presentation.dto.user.response.UserBuyHistoryDto;
import com.group.libraryapp.presentation.dto.user.response.UserInfoResponse;
import com.group.libraryapp.presentation.dto.user.response.UserLoanHistoryDto;
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

    @GetMapping("api/v1/user")
    public ResponseEntity<UserInfoResponse> getUserInfo(@Login UserAuth userAuth) {
        User user = userService.getUserById(userAuth.getId());
        UserInfoResponse response = UserInfoResponse.fromDomain(user);
        return ResponseEntity.ok(response);
    }

    @JwtFilterExclusion
    @PostMapping("api/v1/user")
    public ResponseEntity<Long> createUser(@Valid @RequestBody CreateUserRequest request) {
        Long id = userService.createUser(request.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @DeleteMapping("api/v1/user")
    public ResponseEntity<Object> deleteUser(@Login UserAuth userAuth) {
        userService.deleteUser(userAuth.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("api/v1/user/money")
    public ResponseEntity<Object> chargeMoney(@Login UserAuth userAuth, @Valid @RequestBody ChargeMoneyRequest request) {
        userService.chargeMoney(userAuth.getId(), request.getMoney());
        return ResponseEntity.ok().build();
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
