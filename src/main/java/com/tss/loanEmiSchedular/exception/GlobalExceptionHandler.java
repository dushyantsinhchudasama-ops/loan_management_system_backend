package com.tss.loanEmiSchedular.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            String message,
            HttpStatus status,
            HttpServletRequest request,
            Map<String, String> errors
    ) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(message);
        error.setStatus(status.value());
        error.setPath(request.getRequestURI());
        error.setTimestamp(LocalDateTime.now());
        error.setErrors(errors);

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
        return new ResponseEntity<>("Access is denied: you do not have permission to perform this action",
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(
            ApplicationException ex,
            HttpServletRequest request) {

        log.error("Application Exception: " + ex);
        return buildErrorResponse(
                ex.getMessage(),
                ex.getStatus(),
                request,
                null
        );
    }


    @ExceptionHandler(OtpMaxAttemptsExceededException.class)
    public ResponseEntity<String> handleOtpMaxAttemptsExceeded(OtpMaxAttemptsExceededException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }




    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        log.error("Method Argument Exception: " + ex);

        return buildErrorResponse(
                "Validation failed",
                HttpStatus.BAD_REQUEST,
                request,
                errors
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request) {

        log.error("Bad Credentials Exception: " + ex);
        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED,
                request,
                null
        );
    }



    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            NoResourceFoundException ex,
            HttpServletRequest request) {

        return buildErrorResponse(
                "API endpoint not found",
                HttpStatus.NOT_FOUND,
                request,
                null
        );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDenied(
            org.springframework.security.authorization.AuthorizationDeniedException ex,
            HttpServletRequest request) {

        return buildErrorResponse(
                "You do not have permission to perform this action",
                HttpStatus.FORBIDDEN,
                request,
                null
        );
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Generic Exception: " + ex);
        return buildErrorResponse(
                "Something went wrong",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request,
                null
        );
    }

}
