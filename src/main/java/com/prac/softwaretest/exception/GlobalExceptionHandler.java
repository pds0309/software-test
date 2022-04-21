package com.prac.softwaretest.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MemberNotFoundException.class})
    public ResponseEntity<ErrorResponse> MemberNotFoundExceptionHandler(MemberNotFoundException e) {
        ErrorResponse errorResponse =
                ErrorResponse.builder()
                        .errorCode(ErrorCode.NOT_FOUND)
                        .details(e.getMessage())
                        .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
