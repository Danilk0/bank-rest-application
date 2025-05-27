package com.example.bankcards.exception;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Slf4j
@RestControllerAdvice(basePackages = "com.example.bankcards.controller")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleRuntimeException(Exception ex){
        ApiError error = null;
        log.error(ex.getMessage());

        if(ex instanceof TokenExpiredException){
            error = new ApiError(403, "Authentication token expired!", new Date());
            return new ResponseEntity<>(error, HttpStatusCode.valueOf(403));
        }
        if(ex instanceof SignatureVerificationException){
            error = new ApiError(403, "Invalid JWT signature! Check the JWT token or authenticate again!", new Date());
            return new ResponseEntity<>(error, HttpStatusCode.valueOf(403));
        }
        if(ex instanceof UserEmailDuplicateException){
            error = new ApiError(409, "Change email!", new Date());
            return new ResponseEntity<>(error, HttpStatusCode.valueOf(409));
        }
        if(ex instanceof UserBlockedException){
            error = new ApiError(401, "User block!", new Date());
            return new ResponseEntity<>(error, HttpStatusCode.valueOf(401));
        }
        error = new ApiError(404, ex.getMessage(), new Date());
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(500));
    }
}

