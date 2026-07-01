package com.mayara.openlibrary.exception;

import com.mayara.openlibrary.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(BookNotFoundException ex) {

        return ResponseEntity.status(404)
                .body(new ErrorResponse(404, ex.getMessage()));
    }
}