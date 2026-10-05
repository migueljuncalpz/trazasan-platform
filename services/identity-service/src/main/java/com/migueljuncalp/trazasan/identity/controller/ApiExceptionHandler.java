package com.migueljuncalp.trazasan.identity.controller;

import com.migueljuncalp.trazasan.identity.service.exceptions.InvalidCredentialsException;
import com.migueljuncalp.trazasan.identity.service.exceptions.InvalidRefreshTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    ResponseEntity<ProblemDetail> handleInvalidCredentials(
        InvalidCredentialsException exception
    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.UNAUTHORIZED,
            exception.getMessage()
        );

        problem.setTitle("Autenticación fallida");

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(problem);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    ResponseEntity<ProblemDetail> handleInvalidRefreshToken(
        InvalidRefreshTokenException exception
    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.UNAUTHORIZED,
            exception.getMessage()
        );

        problem.setTitle("Sesión no válida");

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(problem);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ProblemDetail> handleInvalidJson(
            HttpMessageNotReadableException exception
    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "El cuerpo de la petición no contiene un JSON válido"
        );

        problem.setTitle("JSON incorrecto");

        return ResponseEntity
                .badRequest()
                .body(problem);
    }
}