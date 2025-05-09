package com.directa24.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceAccessException, typically caused by issues like UnknownHostException.
     *
     * @param ex The exception thrown during the API call.
     * @return A ResponseEntity with a custom error message and HTTP status.
     */
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<String> handleResourceAccessException(ResourceAccessException ex) {
        String errorMessage = "Error accessing the external movie API. Please try again later.";
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorMessage);
    }

    /**
     * Handles UnknownHostException specifically, providing a more detailed error message.
     *
     * @param ex The root cause exception.
     * @return A ResponseEntity with a custom error message and HTTP status.
     */
    @ExceptionHandler(java.net.UnknownHostException.class)
    public ResponseEntity<String> handleUnknownHostException(java.net.UnknownHostException ex) {
        String errorMessage = "The external movie API host could not be resolved. Please check your network connection.";
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorMessage);
    }
}
