package com.group.libraryapp.presentation.controller.user.response;

import com.group.libraryapp.domain.model.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {
    private String name;
    private Integer age;
    private String email;
    private Integer money;

    public static UserInfoResponse fromDomain(User user) {
        return UserInfoResponse.builder()
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .money(user.getMoney())
                .build();
    }
}
