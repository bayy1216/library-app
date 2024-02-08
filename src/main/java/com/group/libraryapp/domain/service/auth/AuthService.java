package com.group.libraryapp.domain.service.auth;

import com.group.libraryapp.core.jwt.JwtToken;
import com.group.libraryapp.core.jwt.JwtUtils;
import com.group.libraryapp.core.jwt.UserAuth;
import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.domain.port.user.UserRepository;
import com.group.libraryapp.domain.type.UserType;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public JwtToken login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        if(!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        UserAuth userAuth = UserAuth.builder()
                .id(user.getId())
                .type(UserType.USER)
                .build();
        return jwtUtils.createToken(userAuth);
    }

}
