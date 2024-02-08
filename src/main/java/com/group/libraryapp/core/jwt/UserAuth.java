package com.group.libraryapp.core.jwt;

import com.group.libraryapp.domain.type.UserType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAuth {
    private final Long id;
    private final UserType type;
}
