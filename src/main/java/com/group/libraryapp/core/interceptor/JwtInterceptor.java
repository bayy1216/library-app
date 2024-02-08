package com.group.libraryapp.core.interceptor;

import com.group.libraryapp.core.jwt.JwtUtils;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Authorization] 헤더에서 토큰을 추출한뒤 유효성을 검사한다.
 * <p>
 * 유효한 토큰이면 [userAuth]를 request에 넣어준다.
 * <p>
 * 이후 [JwtLoginResolver]에서 [userAuth]를 컨트롤러의 매개변수로 넣어준다.
 * @see JwtLoginResolver
 */
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtils jwtUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean checkAnnotation = checkAnnotation(handler);
        if(checkAnnotation) return true;

        String rawToken = request.getHeader("Authorization");
        String token = jwtUtils.parseHeader(rawToken, true);
        if(jwtUtils.validateToken(token)){
            request.setAttribute("userAuth", jwtUtils.extractUserAuth(token));
        }else{
            throw new JwtException("유효하지 않은 토큰입니다.");
        }


        return true;
    }


    /**
     * [JwtFilterExclusion] 어노테이션이 있는지 확인한다.
     */
    private boolean checkAnnotation(Object handler){
        HandlerMethod handlerMethod=(HandlerMethod) handler;
        return handlerMethod.getMethodAnnotation(JwtFilterExclusion.class) != null;
    }
}
