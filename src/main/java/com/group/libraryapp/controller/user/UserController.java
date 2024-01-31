package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.book.response.BookSummaryDto;
import com.group.libraryapp.dto.common.response.PagingResponse;
import com.group.libraryapp.dto.user.request.*;
import com.group.libraryapp.dto.user.response.UserBuyHistoryDto;
import com.group.libraryapp.dto.user.response.UserInfoResponse;
import com.group.libraryapp.dto.user.response.UserLoanHistoryDto;
import com.group.libraryapp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("api/v1/user")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestHeader("Authorization") Long userId) {
        UserInfoResponse response = userService.getUserInfo(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("api/v1/user")
    public ResponseEntity<Long> createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(1L);
    }

    @DeleteMapping("api/v1/user")
    public ResponseEntity<Object> deleteUser(@RequestHeader("Authorization") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("api/v1/user/money")
    public ResponseEntity<Object> chargeMoney(@RequestHeader("Authorization") Long userId, @RequestBody ChargeMoneyRequest request) {
        userService.chargeMoney(userId, request.getMoney());
        return ResponseEntity.ok().build();
    }

    @GetMapping("api/v1/user/book/loan")
    public ResponseEntity<PagingResponse<UserLoanHistoryDto>> pagingLoanHistory(
            @RequestHeader("Authorization") Long userId,
            @RequestParam("page") int page
    ){
        PagingResponse<UserLoanHistoryDto> response = userService.pagingLoanHistory(userId, page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("api/v1/user/book/buy")
    public ResponseEntity<PagingResponse<UserBuyHistoryDto>> pagingBuyHistory(
            @RequestHeader("Authorization") Long userId,
            @RequestParam("page") int page
    ){
        PagingResponse<UserBuyHistoryDto> response = userService.pagingBuyHistory(userId, page);
        return ResponseEntity.ok(response);
    }


}
