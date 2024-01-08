package com.picpay.challenge.domain.transaction.validators;

import com.picpay.challenge.domain.transaction.DTO.PostTransactionDTO;
import com.picpay.challenge.domain.transaction.exception.InvalidTransactionException;
import org.springframework.stereotype.Component;

@Component
public class SamePayerPayeeException implements TransactionValidator {
    @Override
    public void validate(PostTransactionDTO data) throws RuntimeException {
        if (data.payer().equals(data.payee())) {
            throw new InvalidTransactionException("The payee and the payer is the same");
        }
    }
}
