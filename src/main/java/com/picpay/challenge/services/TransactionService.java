package com.picpay.challenge.services;

import com.picpay.challenge.DTO.email.GetEmailServiceDTO;
import com.picpay.challenge.DTO.transaction.PostTransactionDTO;
import com.picpay.challenge.DTO.transaction.TransactionReturnDTO;
import com.picpay.challenge.DTO.transactionAuth.GetTransactionAuthDTO;
import com.picpay.challenge.domain.transaction.Transaction;
import com.picpay.challenge.domain.transaction.TransactionRepository;
import com.picpay.challenge.domain.transaction.exception.InvalidTransactionException;
import com.picpay.challenge.domain.transaction.validators.TransactionValidator;
import com.picpay.challenge.domain.user.User;
import com.picpay.challenge.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private List<TransactionValidator> validators;

    @Autowired
    private EmailServiceClient emailServiceClient;

    @Autowired
    private TransactionAuthServiceClient transactionAuthServiceClient;

    public TransactionReturnDTO makeTransaction(PostTransactionDTO data) {
        User payer = userService.findOne(data.payer()).orElse(null);
        User payee = userService.findOne(data.payee()).orElse(null);

        if (isUserNull(payer)) {
            throw new InvalidTransactionException("The payer doesn't exists");
        }

        if (isUserNull(payee)) {
            throw new InvalidTransactionException("The payee doesn't exists");
        }

        validators.forEach(validator -> validator.validate(data));

        GetTransactionAuthDTO transactionAuth = transactionAuthServiceClient.requestAuthorization();

        if (!isAuthorized(transactionAuth.message())) {
            throw new InvalidTransactionException("Transaction not authorized");
        }

        payer.withdraw(data.value());
        payee.deposit(data.value());

        Transaction transaction = new Transaction(
                null,
                data.value(),
                payer,
                payee,
                LocalDateTime.now()
        );

        transactionRepository.save(transaction);

        GetEmailServiceDTO emailStatus = emailServiceClient.sendEmail();

        return new TransactionReturnDTO(payer.getId(), payee.getId(), data.value(), emailStatus.message());
    }

    private boolean isUserNull(User user) {
        return user == null;
    }

    private boolean isAuthorized(String message) {
        return message.equals("Autorizado");
    }
}
