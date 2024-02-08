package com.group.libraryapp.core.jwt;

import com.group.libraryapp.domain.type.UserType;
import lombok.Builder;
import lombok.Getter;

/**
 * JWT 토큰에서 추출한 사용자 정보를 담는 객체
 * [Login] 어노테이션이 붙은 컨트롤러의 매개변수로 주입됨
 */
@Getter
@Builder
public class UserAuth {
    private final Long id;
    private final UserType type;
}
