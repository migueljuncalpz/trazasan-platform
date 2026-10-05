package com.migueljuncalp.trazasan.identity.service.exceptions;

public class InvalidCredentialsException extends RuntimeException{
      public InvalidCredentialsException(){
        super("The credentials are not correct");
    }
}
