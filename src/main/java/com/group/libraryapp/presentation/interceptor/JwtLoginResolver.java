package com.group.libraryapp.presentation.interceptor;

import com.group.libraryapp.core.jwt.UserAuth;
import com.group.libraryapp.presentation.interceptor.annotation.Login;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * [JwtInterceptor]에서 넣어준 [UserAuth]를
 * 컨트롤러의 메서드의 파라미터로 넣어주는 Resolver.
 * <p>
 * 컨트롤러에서 매개변수에 (@Login UserAuth userAuth)로 사용한다
 * @see JwtInterceptor
 * @see Login
 * @see UserAuth
 */
@Component
public class JwtLoginResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class)
                && parameter.getParameterType().equals(UserAuth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return webRequest.getAttribute("userAuth", NativeWebRequest.SCOPE_REQUEST);
    }
}
