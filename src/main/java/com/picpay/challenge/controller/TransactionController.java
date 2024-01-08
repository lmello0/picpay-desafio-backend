package com.picpay.challenge.controller;

import com.picpay.challenge.services.TransactionService;
import com.picpay.challenge.DTO.transaction.PostTransactionDTO;
import com.picpay.challenge.DTO.transaction.TransactionReturnDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> makeTransaction(@RequestBody @Valid PostTransactionDTO data) {
        TransactionReturnDTO transaction = transactionService.makeTransaction(data);

        return ResponseEntity.ok(transaction);
    }
}
