package com.group.libraryapp.core.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtToken {
    private final String accessToken;
    private final String refreshToken;
}
