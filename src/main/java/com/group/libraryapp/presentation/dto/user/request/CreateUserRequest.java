package com.group.libraryapp.presentation.dto.user.request;

import lombok.*;

@Getter
public class CreateUserRequest {
    private String name;
    private Integer age;
    private String email;
    private String password;
}
