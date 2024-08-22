package com.codestorm.exceptionhandlingjpa.exceptions;

import com.codestorm.exceptionhandlingjpa.controller.UserController;
import com.codestorm.exceptionhandlingjpa.dto.ErrorResponseDto;
import com.codestorm.exceptionhandlingjpa.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Method;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomExceptionHandlerTest {

    private final CustomExceptionHandler exceptionHandler = new CustomExceptionHandler();

    @Test
    void testHandleUserNotFoundException() {
        // Arrange
        UserNotFoundException exception = new UserNotFoundException("User not found");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("/api/users/1");

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleUserNotFoundException(exception, request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(ErrorResponseDto.class);

        ErrorResponseDto responseDto = (ErrorResponseDto) response.getBody();
        assertNotNull(responseDto);
        assertThat(responseDto.message()).isEqualTo("User not found");
        assertThat(responseDto.description()).isEqualTo("Path is /api/users/1");
        assertThat(responseDto.timestamp()).isNotNull(); // Check that timestamp is not null
    }

    @Test
    void testHandleUnableToProcessException() {
        // Arrange
        UnableToProcessException exception = new UnableToProcessException("Unable to process request");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("/api/process");

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleUnableToProcessException(exception, request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isInstanceOf(ErrorResponseDto.class);

        ErrorResponseDto responseDto = (ErrorResponseDto) response.getBody();
        assertNotNull(responseDto);
        assertThat(responseDto.message()).isEqualTo("Unable to process request");
        assertThat(responseDto.description()).isEqualTo("Path is /api/process");
        assertThat(responseDto.timestamp()).isNotNull(); // Ensure timestamp is not null
    }

    @Test
    void testHandleMethodArgumentNotValid() throws NoSuchMethodException {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("userDto", "username", "Username must not be null");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        Method method = UserController.class.getMethod("createUser", UserDto.class);
        MethodParameter parameter = new MethodParameter(method, 0);

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(parameter, bindingResult);

        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        WebRequest request = mock(WebRequest.class);

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleMethodArgumentNotValid(exception, headers, status, request);

        // Assert
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isInstanceOf(ErrorResponseDto.class);

        ErrorResponseDto responseDto = (ErrorResponseDto) response.getBody();
        assertNotNull(responseDto);
        assertThat(responseDto.message()).isEqualTo("Username must not be null");
        assertThat(responseDto.description()).isEqualTo("Error(s) found: 1");
        assertThat(responseDto.timestamp()).isNotNull(); // Ensure timestamp is not null
    }
}