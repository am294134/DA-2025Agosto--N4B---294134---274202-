package com.example.obligatorio_dda;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.obligatorio_dda.Modelo.PeajeException;

@RestControllerAdvice
public class GlobalExceptionHanlder {
    @ExceptionHandler(PeajeException.class)
    public ResponseEntity<String> manejarException(PeajeException ex) {
        return ResponseEntity.status(299).body(ex.getMessage());
    }
}
