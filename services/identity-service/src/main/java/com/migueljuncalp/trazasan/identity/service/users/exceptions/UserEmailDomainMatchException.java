package com.migueljuncalp.trazasan.identity.service.users.exceptions;

public class UserEmailDomainMatchException extends RuntimeException {
    public UserEmailDomainMatchException() {
        super("The user email domain does not match with hospital domain");
    }
}
