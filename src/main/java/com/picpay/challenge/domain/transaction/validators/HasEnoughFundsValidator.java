package com.picpay.challenge.domain.transaction.validators;

import com.picpay.challenge.DTO.transaction.PostTransactionDTO;
import com.picpay.challenge.domain.transaction.exception.NotEnoughMoneyException;
import com.picpay.challenge.domain.user.User;
import com.picpay.challenge.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HasEnoughFundsValidator implements TransactionValidator {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void validate(PostTransactionDTO data) throws RuntimeException {
        User payer = userRepository.getReferenceById(data.payer());

        if (payer.getWallet() < data.value()) {
            throw new NotEnoughMoneyException();
        }
    }
}
