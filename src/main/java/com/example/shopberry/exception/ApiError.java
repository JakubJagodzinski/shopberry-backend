package com.example.shopberry.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class ApiError {

    private final int status;

    private final String message;

    private final LocalDateTime timestamp;

    private final Map<String, String> errors;

}
