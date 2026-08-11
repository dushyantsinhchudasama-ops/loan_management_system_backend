package com.tss.loanEmiSchedular.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
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

    // Covers every custom application exception, including UserApiException,
    // ResourceNotFoundException, UserAlreadyExistsException, BusinessException,
    // InvalidPageException, EmailSendingException, and the custom
    // AccessDeniedException — since they all extend ApplicationException,
    // one handler is enough for all of them.
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleException(ApplicationException applicationException, HttpServletRequest httpServletRequest) {
        log.error("Application Exception: " + applicationException);
        return buildErrorResponse(
                applicationException.getMessage(),
                applicationException.getStatus(),
                httpServletRequest,
                null
        );
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEnum(HttpMessageNotReadableException ex,
                                                            HttpServletRequest request) {
        log.error("Json parse Exception: " + ex);
        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
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

    // Newer Spring Security 6 method-security failures (@PreAuthorize on a
    // controller method) surface as this exception.
    @ExceptionHandler(org.springframework.security.authorization.AuthorizationDeniedException.class)
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

    // Older / framework-level access-denied checks (e.g. filter chain
    // authorization, some Spring Security internals) still throw this type
    // instead of AuthorizationDeniedException, so it's handled too.
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleSecurityAccessDenied(
            org.springframework.security.access.AccessDeniedException ex,
            HttpServletRequest request) {

        log.error("Access Denied Exception: " + ex);
        return buildErrorResponse(
                "Access is denied: you do not have permission to perform this action",
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
