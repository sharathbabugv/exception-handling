package com.codestorm.exceptionhandlingjpa.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionTests {

    @Test
    public void testUnableToProcessException() {
        String expectedMessage = "Process failed";
        Exception exception = assertThrows(UnableToProcessException.class, () -> {
            throw new UnableToProcessException(expectedMessage);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testUserNotFoundExceptionException() {
        String expectedMessage = "Process failed";
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException(expectedMessage);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }
}