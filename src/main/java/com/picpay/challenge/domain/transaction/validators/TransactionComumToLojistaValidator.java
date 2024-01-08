package com.picpay.challenge.domain.transaction.validators;

import com.picpay.challenge.domain.transaction.DTO.PostTransactionDTO;
import com.picpay.challenge.domain.transaction.exception.InvalidTransactionException;
import com.picpay.challenge.domain.user.Type;
import com.picpay.challenge.domain.user.User;
import com.picpay.challenge.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionComumToLojistaValidator implements TransactionValidator {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(PostTransactionDTO data) throws RuntimeException {
        User payer = userRepository.getReferenceById(data.payer());

        if (payer.getType() == Type.LOJISTA) {
            throw new InvalidTransactionException("Transaction from LOJISTA to COMUM is invalid");
        }
    }
}
