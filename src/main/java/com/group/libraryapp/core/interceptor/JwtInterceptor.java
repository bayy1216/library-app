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


    private boolean checkAnnotation(Object handler){
        HandlerMethod handlerMethod=(HandlerMethod) handler;
        return handlerMethod.getMethodAnnotation(JwtFilterExclusion.class) != null;
    }
}
