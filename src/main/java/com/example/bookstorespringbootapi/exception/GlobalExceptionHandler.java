package com.example.bookstorespringbootapi.exception;

import com.example.bookstorespringbootapi.payload.ApiResponse;
import com.example.bookstorespringbootapi.payload.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e){
        log.info(e.getMessage());
        log.info(" Error count "+ String.valueOf(e.getBindingResult().getErrorCount()));
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        for(ObjectError oe : allErrors){
            log.info(oe.getDefaultMessage());
        }

        ErrorResponse res = new ErrorResponse();
        res.setStatus(HttpStatus.BAD_REQUEST);
        res.setErrorCount(e.getBindingResult().getErrorCount());
        res.setMessages(e.getBindingResult().getAllErrors().stream().map(objectError -> new String(objectError.getDefaultMessage())).collect(Collectors.toList()));
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

}
