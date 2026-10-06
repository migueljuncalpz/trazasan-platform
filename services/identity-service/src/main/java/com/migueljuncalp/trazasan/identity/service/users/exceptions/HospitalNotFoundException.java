package com.migueljuncalp.trazasan.identity.service.users.exceptions;

public class HospitalNotFoundException extends RuntimeException {
    public HospitalNotFoundException() {
        super("The hospital does not exist");
    }
}
