package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Exception;

public class RateLimitExceededException extends RuntimeException {

    public RateLimitExceededException(String message) {
        super(message);
    }
}
