package com.coinlift.backend.exceptions;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        for (ObjectError objectError : ex.getBindingResult().getGlobalErrors()) {
            errorMessages.add(objectError.getObjectName() + ": " + objectError.getDefaultMessage());
        }

        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation failed", errorMessages.toArray(new String[0]));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ErrorDetails> handleMessagingException(MessagingException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Error sending email", new String[]{ex.getMessage()});

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorDetails> handlePasswordMismatchException(PasswordMismatchException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation failed", new String[]{ex.getMessage()});
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorDetails> handleDuplicateUserException(DuplicateUserException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation failed", new String[]{ex.getMessage()});
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Authentication failed", new String[]{ex.getMessage()});
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
}