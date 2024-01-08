package com.cognizant.employeeManagement.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public  ResponseEntity<ExceptionDetails> handelIllegalStateException (IllegalStateException ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationException.class)
    public  ResponseEntity<ExceptionDetails> authorizationException (AuthorizationException ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(FeignException.class)
    public  ResponseEntity<ExceptionDetails> feginException (FeignException ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ExceptionDetails> handleMissingRequestHeaderException(MissingRequestHeaderException ex){
        ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
    }

}