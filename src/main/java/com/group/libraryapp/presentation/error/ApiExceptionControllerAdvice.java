package com.group.libraryapp.presentation.error;

import com.group.libraryapp.core.exception.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionControllerAdvice {


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> noSuchElementExceptionHandler(HttpServletRequest request, NoSuchElementException e) {
        log.info("NoSuchElementException {}", e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("NoSuchElementException")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : fieldErrors)
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        log.info("MethodArgumentNotValidException {}", errors);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("MethodArgumentNotValidException")
                .message(errors.toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(HttpServletRequest request, IllegalArgumentException e) {
        log.info("IllegalArgumentException {}", e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("IllegalArgumentException")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> jwtExceptionHandler(HttpServletRequest request, JwtException e) {
        log.info("JwtException {}", e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("JwtException")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(HttpServletRequest request, ResourceNotFoundException e) {
        var rawToken = request.getHeader("Authorization");
        log.warn("ResourceNotFoundException {}, token = {}", e, rawToken);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Exception")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
