package com.group.libraryapp.presentation.dto.auth.response;

import com.group.libraryapp.core.jwt.JwtToken;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    private final String accessToken;
    private final String refreshToken;

    public static TokenResponse of(JwtToken jwtToken) {
        return TokenResponse.builder()
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .build();
    }
}
