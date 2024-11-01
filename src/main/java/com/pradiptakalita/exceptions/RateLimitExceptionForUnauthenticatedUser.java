package com.pradiptakalita.exceptions;

public class RateLimitExceptionForUnauthenticatedUser extends RuntimeException{
    public RateLimitExceptionForUnauthenticatedUser(String message) {
        super(message);
    }

    public RateLimitExceptionForUnauthenticatedUser(String message, Throwable cause) {
        super(message, cause);
    }
}
