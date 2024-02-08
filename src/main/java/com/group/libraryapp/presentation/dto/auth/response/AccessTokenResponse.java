package com.group.libraryapp.presentation.dto.auth.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenResponse {
    private final String accessToken;

    public static AccessTokenResponse of(String accessToken) {
        return AccessTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
