package com.pradiptakalita.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFoundException(EntityNotFoundException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());
    }

    @ExceptionHandler(RateLimitExceptionForAuthenticatedUser.class)
    public ProblemDetail rateLimitExceptionForAuthenticatedUser(RateLimitExceptionForAuthenticatedUser ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.TOO_MANY_REQUESTS,ex.getMessage());
    }

    @ExceptionHandler(RateLimitExceptionForUnauthenticatedUser.class)
    public ProblemDetail rateLimitExceptionForUnauthenticatedUser(RateLimitExceptionForAuthenticatedUser ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.TOO_MANY_REQUESTS,ex.getMessage());
    }
}
