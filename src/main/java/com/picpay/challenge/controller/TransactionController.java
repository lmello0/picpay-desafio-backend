package com.picpay.challenge.controller;

import com.picpay.challenge.domain.transaction.Bank;
import com.picpay.challenge.domain.transaction.DTO.PostTransactionDTO;
import com.picpay.challenge.domain.transaction.DTO.TransactionReturnDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private Bank bank;

    @PostMapping
    @Transactional
    public ResponseEntity<?> makeTransaction(@RequestBody @Valid PostTransactionDTO data) {
        TransactionReturnDTO transaction = bank.makeTransaction(data);

        return ResponseEntity.ok(transaction);
    }
}
