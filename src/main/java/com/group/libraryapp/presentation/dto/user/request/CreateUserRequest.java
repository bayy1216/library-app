package com.group.libraryapp.presentation.dto.user.request;

import com.group.libraryapp.domain.model.user.UserCreate;
import lombok.*;

@Getter
public class CreateUserRequest {
    private String name;
    private Integer age;
    private String email;
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
