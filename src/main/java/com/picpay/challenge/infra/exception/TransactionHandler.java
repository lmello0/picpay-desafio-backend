package com.picpay.challenge.infra.exception;

import com.picpay.challenge.domain.transaction.exception.InvalidTransactionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransactionHandler {
    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<?> handleInvalidTransaction(InvalidTransactionException ex) {
        return ResponseEntity
                .badRequest()
                .body(new Response(ex.getMessage()));
    }
}
