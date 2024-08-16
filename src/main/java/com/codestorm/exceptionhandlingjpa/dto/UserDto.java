package com.codestorm.exceptionhandlingjpa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDto(Long id,
                      @NotBlank(message = "Username must not be null") String username,
                      @NotBlank(message = "Email must not be null") @Email(message = "Invalid Email Format") String email) {
}
