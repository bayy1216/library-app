package com.group.libraryapp.presentation.controller.auth;

import com.group.libraryapp.presentation.interceptor.annotation.JwtFilterExclusion;
import com.group.libraryapp.core.jwt.JwtToken;
import com.group.libraryapp.core.jwt.JwtUtils;
import com.group.libraryapp.domain.service.auth.AuthService;
import com.group.libraryapp.presentation.dto.auth.response.AccessTokenResponse;
import com.group.libraryapp.presentation.dto.auth.response.TokenResponse;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @JwtFilterExclusion
    @GetMapping("api/v1/auth/login")
    public TokenResponse login(@RequestHeader("Authorization") String rawHeader) {
        String emailAndPasswordHeader = jwtUtils.parseHeader(rawHeader, false);
        byte[] bytes = Base64Utils.decodeFromString(emailAndPasswordHeader);
        String emailAndPassword = new String(bytes);
        String[] emailAndPasswordArr = emailAndPassword.split(":");

        String email = emailAndPasswordArr[0];
        String password = emailAndPasswordArr[1];


        JwtToken jwtToken = authService.login(email, password);
        return TokenResponse.of(jwtToken);
    }

    @JwtFilterExclusion
    @PostMapping("api/v1/auth/reissue")
    public AccessTokenResponse reissue(@RequestHeader("Authorization") String refreshRawToken) {
        String refreshToken = jwtUtils.parseHeader(refreshRawToken, true);
        if(!jwtUtils.validateToken(refreshToken)) {
            throw new JwtException("유효하지 않은 토큰입니다.");
        }
        String accessToken = jwtUtils.reissueAccessToken(refreshToken);
        return AccessTokenResponse.of(accessToken);
    }
}
