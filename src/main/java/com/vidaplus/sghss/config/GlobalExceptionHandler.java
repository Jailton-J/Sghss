package com.vidaplus.sghss.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
    List<Map<String, String>> errors = new ArrayList<>();

    ex.getBindingResult().getFieldErrors().forEach(error -> {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("campo", error.getField());
        errorDetails.put("mensagem", error.getDefaultMessage());
        errors.add(errorDetails);
    });

    return ResponseEntity.badRequest().body(errors);
}
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + ex.getMessage());
    }
}
