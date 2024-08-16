package com.codestorm.exceptionhandlingjpa.exceptions;

import com.codestorm.exceptionhandlingjpa.dto.ErrorResponseDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception, WebRequest request){
        ErrorResponseDto responseDto = new ErrorResponseDto(exception.getMessage(), String.format("Path is %s", request.getDescription(false)), LocalDateTime.now());
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnableToProcessException.class)
    public final ResponseEntity<Object> handleUnableToProcessException(UnableToProcessException exception, WebRequest request){
        ErrorResponseDto responseDto = new ErrorResponseDto(exception.getMessage(), String.format("Path is %s", request.getDescription(false)), LocalDateTime.now());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());


        String errorMessage = String.join(" || ", errorMessages);

        ErrorResponseDto responseDto = new ErrorResponseDto(errorMessage, String.format("Error(s) found: %d", errorMessages.size()), LocalDateTime.now());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
