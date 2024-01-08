package com.picpay.challenge.domain.transaction;

import com.picpay.challenge.domain.transaction.DTO.PostTransactionDTO;
import com.picpay.challenge.domain.transaction.DTO.TransactionReturnDTO;
import com.picpay.challenge.domain.transaction.exception.InvalidTransactionException;
import com.picpay.challenge.domain.transaction.validators.TransactionValidator;
import com.picpay.challenge.domain.user.User;
import com.picpay.challenge.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Bank {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private List<TransactionValidator> validators;

    public TransactionReturnDTO makeTransaction(PostTransactionDTO data) {
        User payer = userRepository.findById(data.payer()).orElse(null);
        User payee = userRepository.findById(data.payee()).orElse(null);

        if (payer == null) {
            throw new InvalidTransactionException("The payer doesn't exists");
        }

        if (payee == null) {
            throw new InvalidTransactionException("The payee doesn't exists");
        }

        validators.forEach(validator -> validator.validate(data));

        payer.withdraw(data.value());
        payee.deposit(data.value());

        Transaction transaction = new Transaction(
                null,
                data.value(),
                payer,
                payee
        );

        transactionRepository.save(transaction);

        return new TransactionReturnDTO(payer.getId(), payee.getId(), data.value());
    }
}
