package com.picpay.challenge.domain.transaction.validators;

import com.picpay.challenge.domain.transaction.DTO.PostTransactionDTO;

public interface TransactionValidator {
    void validate(PostTransactionDTO data) throws RuntimeException;
}
