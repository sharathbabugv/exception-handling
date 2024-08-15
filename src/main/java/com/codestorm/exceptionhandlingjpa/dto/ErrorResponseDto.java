package com.codestorm.exceptionhandlingjpa.dto;

import java.time.LocalDateTime;

public record ErrorResponseDto(String message, String description, LocalDateTime timestamp) {
}
