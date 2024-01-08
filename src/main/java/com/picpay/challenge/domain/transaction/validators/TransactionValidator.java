package com.picpay.challenge.domain.transaction.validators;

import com.picpay.challenge.DTO.transaction.PostTransactionDTO;

public interface TransactionValidator {
    void validate(PostTransactionDTO data) throws RuntimeException;
}
