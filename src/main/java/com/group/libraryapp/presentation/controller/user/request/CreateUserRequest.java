package com.group.libraryapp.presentation.controller.user.request;

import com.group.libraryapp.domain.model.user.UserCreate;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class CreateUserRequest {
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @Positive(message = "나이는 0보다 커야 합니다.")
    @NotNull(message = "나이는 필수입니다.")
    private Integer age;

    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    public UserCreate toDomain() {
        return UserCreate.builder()
                .name(name)
                .age(age)
                .email(email)
                .password(password)
                .build();
    }
}
