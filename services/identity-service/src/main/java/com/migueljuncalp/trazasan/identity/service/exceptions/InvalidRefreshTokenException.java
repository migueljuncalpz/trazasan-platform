package com.migueljuncalp.trazasan.identity.service.exceptions;

public class InvalidRefreshTokenException extends RuntimeException  {
    public InvalidRefreshTokenException() {
        super("Imposible to refresh session, session currently available.");
    }
}
