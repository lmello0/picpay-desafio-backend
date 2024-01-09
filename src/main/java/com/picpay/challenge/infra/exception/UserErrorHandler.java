package com.picpay.challenge.infra.exception;

import com.picpay.challenge.domain.user.exception.UserInactiveException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserErrorHandler {

    @ExceptionHandler(UserInactiveException.class)
    public ResponseEntity<?> userInactiveHandler(UserInactiveException ex) {
        return ResponseEntity.badRequest().body(
                new Response(ex.getMessage())
        );
    }
}
