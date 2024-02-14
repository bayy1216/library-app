package com.group.libraryapp.presentation.interceptor;

import com.group.libraryapp.core.jwt.JwtUtils;
import com.group.libraryapp.core.jwt.UserAuth;
import com.group.libraryapp.core.type.UserType;
import com.group.libraryapp.presentation.interceptor.annotation.Admin;
import com.group.libraryapp.presentation.interceptor.annotation.JwtFilterExclusion;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Authorization] 헤더에서 토큰을 추출한뒤 유효성을 검사한다.
 * <p>
 * 유효한 토큰이면 [userAuth]를 request에 넣어준다.
 * <p>
 * 이후 [JwtLoginResolver]에서 [userAuth]를 컨트롤러의 매개변수로 넣어준다.
 *
 * @see JwtLoginResolver
 */
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean checkJwtFilter = checkAnnotation(handler, JwtFilterExclusion.class);
        if (checkJwtFilter) return true;

        String rawToken = request.getHeader("Authorization");
        String token = jwtUtils.parseHeader(rawToken, true);
        if (!jwtUtils.validateToken(token)) {
            throw new JwtException("유효하지 않은 토큰입니다.");
        }

        UserAuth userAuth = jwtUtils.extractUserAuth(token);

        /**
         * [Admin] 어노테이션이 있는지 확인한다.
         * 있으면 [userAuth]의 타입이 [ADMIN]인지 확인한다.
         * 아니면 [JwtException]을 던진다.
         * @see Admin
         */
        boolean checkAdminFilter = checkAnnotation(handler, Admin.class);
        if (checkAdminFilter) {
            if (userAuth.getType() != UserType.ADMIN) {
                throw new JwtException("관리자 권한이 필요합니다.");
            }
            return true;
        }


        request.setAttribute("userAuth", userAuth);
        return true;
    }


    /**
     * [JwtFilterExclusion] 어노테이션이 있는지 확인한다.
     */
    private boolean checkAnnotation(Object handler, Class clazz) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        return handlerMethod.getMethodAnnotation(clazz) != null;
    }
}
