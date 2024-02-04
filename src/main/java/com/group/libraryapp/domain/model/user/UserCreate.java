package com.group.libraryapp.domain.model.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreate {
    private String name;
    private Integer age;
    private String email;
    private String password;
}
