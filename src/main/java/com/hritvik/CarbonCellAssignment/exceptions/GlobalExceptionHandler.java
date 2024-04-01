package com.hritvik.CarbonCellAssignment.exceptions;

import com.hritvik.CarbonCellAssignment.utils.Response;
import com.hritvik.CarbonCellAssignment.utils.ResponseUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    ResponseUtil responseUtil;

    // JWT Exceptions
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Response> handleInvalidJwtSignature(Exception ex) {
        log.error("Invalid JWT signature:", ex);
        return new ResponseEntity<>(responseUtil.unauthorizedResponse("Invalid JWT signature."), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Response> handleExpiredJwt(Exception ex) {
        log.error("Expired JWT:", ex);
        return new ResponseEntity<>(responseUtil.unauthorizedResponse("Expired JWT."), HttpStatus.UNAUTHORIZED);
    }

    // Spring Security Exceptions
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response> handleAccessDenied(AccessDeniedException ex) {
        log.error("Access denied:", ex);
        return new ResponseEntity<>(responseUtil.forbiddenResponse("You do not have permission to access this resource."), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Invalid credentials:", ex);
        return new ResponseEntity<>(responseUtil.unauthorizedResponse("Invalid username or password."), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Response> handleUserNotFound(UsernameNotFoundException ex) {
        log.error("User not found:", ex);
        return new ResponseEntity<>(responseUtil.unauthorizedResponse("User not found."), HttpStatus.UNAUTHORIZED);
    }

    // Validation Exceptions
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleConstraintViolation(ConstraintViolationException ex) {
        String message = "Validation failed: " + ex.getMessage();
        return new ResponseEntity<>(responseUtil.badRequestResponse(message), HttpStatus.BAD_REQUEST);
    }

    // Method Argument Type Mismatch
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid request parameter: " + ex.getMessage();
        return new ResponseEntity<>(responseUtil.badRequestResponse(message), HttpStatus.BAD_REQUEST);
    }

    // Generic Exception Handler (place at the end)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception ex) {
        log.error("An unexpected exception occurred:", ex);
        return new ResponseEntity<>(responseUtil.internalServerErrorResponse("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<Response> handleInvalidJwtToken(InvalidJwtTokenException ex) {
        log.error("Invalid JWT token:", ex);
        return new ResponseEntity<>(responseUtil.unauthorizedResponse("Invalid JWT token. " + ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    public static class InvalidJwtTokenException extends RuntimeException {
        public InvalidJwtTokenException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<Response> handleJwtValidation(JwtValidationException ex) {
        log.error("inside JwtValidationException handler");
        return new ResponseEntity<>(responseUtil.unauthorizedResponse("Invalid JWT token. " + ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }
    public static class JwtValidationException extends RuntimeException {
        public JwtValidationException(String message) {
            super(message);
            log.error("Inside JwtValidationException:");
        }
    }
}