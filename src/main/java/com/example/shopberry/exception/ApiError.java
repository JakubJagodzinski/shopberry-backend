package com.example.shopberry.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
@JsonPropertyOrder({"status", "message", "timestamp", "errors"})
public class ApiError {

    private final int status;

    private final String message;

    private final LocalDateTime timestamp;

    private final Map<String, String> errors;

}
