package com.example.bookstorespringbootapi.exception;

import com.example.bookstorespringbootapi.payload.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiResponse> handleRegistrationException(InvalidInputException e){
        ApiResponse res = new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException e){
        ApiResponse res = new ApiResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

}
