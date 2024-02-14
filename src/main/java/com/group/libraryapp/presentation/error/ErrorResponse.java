package com.group.libraryapp.presentation.error;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final String error;
    private final String message;
}
