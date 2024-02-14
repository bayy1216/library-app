package com.group.libraryapp.domain.service.auth;

import com.group.libraryapp.core.exception.ResourceNotFoundException;
import com.group.libraryapp.core.jwt.JwtToken;
import com.group.libraryapp.core.jwt.JwtUtils;
import com.group.libraryapp.core.jwt.UserAuth;
import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.domain.port.user.UserRepository;
import com.group.libraryapp.core.type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JwtToken login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", email)
        );
        boolean isMatches = bCryptPasswordEncoder.matches(password, user.getPassword());
        if(!isMatches) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        UserAuth userAuth = UserAuth.builder()
                .id(user.getId())
                .type(UserType.USER)
                .build();
        return jwtUtils.createToken(userAuth);
    }

}
