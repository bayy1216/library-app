package com.group.libraryapp.core.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * [UserAuth]를 사용할 컨트롤러의 매개변수에 붙이는 어노테이션
 * @see com.group.libraryapp.core.jwt.UserAuth
 * @see JwtInterceptor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Login {
}
