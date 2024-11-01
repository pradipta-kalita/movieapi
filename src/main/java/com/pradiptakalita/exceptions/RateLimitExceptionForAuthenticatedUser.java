package com.pradiptakalita.exceptions;

public class RateLimitExceptionForAuthenticatedUser extends RuntimeException {
    public RateLimitExceptionForAuthenticatedUser(String message) {
        super(message);
    }

    public RateLimitExceptionForAuthenticatedUser(String message, Throwable cause) {
        super(message, cause);
    }
}
