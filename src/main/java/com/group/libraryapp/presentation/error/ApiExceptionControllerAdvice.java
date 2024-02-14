package com.group.libraryapp.presentation.error;

import com.group.libraryapp.core.exception.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiExceptionControllerAdvice {


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> noSuchElementExceptionHandler(NoSuchElementException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("NoSuchElementException")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : fieldErrors)
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("MethodArgumentNotValidException")
                .message(errors.toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("IllegalArgumentException")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> jwtExceptionHandler(JwtException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("JwtException")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(ResourceNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Exception")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
