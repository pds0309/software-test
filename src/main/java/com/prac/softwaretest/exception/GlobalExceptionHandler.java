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

    // 원래 커스텀 해야되지만 귀찮아서 그냥 함
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> RuntimeExceptionHandler(RuntimeException e) {
        ErrorResponse errorResponse =
                ErrorResponse.builder()
                        .errorCode(ErrorCode.CONFLICTED_REQUEST)
                        .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
