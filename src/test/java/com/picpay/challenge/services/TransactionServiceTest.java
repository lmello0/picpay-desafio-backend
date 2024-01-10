package com.picpay.challenge.services;

import com.picpay.challenge.DTO.email.GetEmailServiceDTO;
import com.picpay.challenge.DTO.transaction.PostTransactionDTO;
import com.picpay.challenge.DTO.transaction.TransactionReturnDTO;
import com.picpay.challenge.DTO.transactionAuth.GetTransactionAuthDTO;
import com.picpay.challenge.domain.transaction.Transaction;
import com.picpay.challenge.domain.transaction.TransactionRepository;
import com.picpay.challenge.domain.transaction.validators.HasEnoughFundsValidator;
import com.picpay.challenge.domain.transaction.validators.SamePayerPayeeException;
import com.picpay.challenge.domain.transaction.validators.TransactionComumToLojistaValidator;
import com.picpay.challenge.domain.transaction.validators.TransactionValidator;
import com.picpay.challenge.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private List<TransactionValidator> validators;

    @Mock
    private HasEnoughFundsValidator enoughFundsValidator;

    @Mock
    private SamePayerPayeeException samePayerPayeeValidator;

    @Mock
    private TransactionComumToLojistaValidator transactionComumToLojistaValidator;

    @Mock
    private EmailServiceClient emailServiceClient;

    @Mock
    private TransactionAuthServiceClient transactionAuthServiceClient;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        validators.add(enoughFundsValidator);
        validators.add(samePayerPayeeValidator);
        validators.add(transactionComumToLojistaValidator);
    }

    @Test
    @DisplayName("Should create a transaction succesfully if all data is correct")
    void makeTransactionCase1() {
        // given
        String payerID = UUID.randomUUID().toString();
        String payeeID = UUID.randomUUID().toString();

        User payer = mock(User.class);
        User payee = mock(User.class);

        int value = 100_00;

        Transaction transaction = new Transaction(null, value, payer, payee, LocalDateTime.now());

        TransactionReturnDTO expectedReturn = new TransactionReturnDTO(payerID, payeeID, value, true);

        // when
        when(payee.getId()).thenReturn(payeeID);
        when(payer.getId()).thenReturn(payerID);

        when(userService.findOne(payerID)).thenReturn(payer);
        when(userService.findOne(payeeID)).thenReturn(payee);

        when(transactionAuthServiceClient.requestAuthorization()).thenReturn(new GetTransactionAuthDTO("Autorizado"));

        when(emailServiceClient.sendEmail()).thenReturn(new GetEmailServiceDTO(true));

        PostTransactionDTO transactionData = new PostTransactionDTO(value, payerID, payeeID);
        TransactionReturnDTO transactionReturn = transactionService.makeTransaction(transactionData);

        // then
        verify(payer, times(1)).withdraw(value);
        verify(payee, times(1)).deposit(value);

        validators.forEach(validator -> {
            verify(validator, times(1)).validate(transactionData);
        });

        verify(transactionRepository, times(1)).save(transaction);

        verify(emailServiceClient, times(1)).sendEmail();

        assertThat(transactionReturn).isEqualTo(expectedReturn);
    }

    @Test
    @DisplayName("Should fail when transaction is not authorized")
    void makeTransactionCase2() {
        // given
        String payerID = UUID.randomUUID().toString();
        String payeeID = UUID.randomUUID().toString();

        User payer = mock(User.class);
        User payee = mock(User.class);

        int value = 100_00;

        Transaction transaction = new Transaction(null, value, payer, payee, LocalDateTime.now());

        // when
        when(payee.getId()).thenReturn(payeeID);
        when(payer.getId()).thenReturn(payerID);

        when(userService.findOne(payerID)).thenReturn(payer);
        when(userService.findOne(payeeID)).thenReturn(payee);

        when(emailServiceClient.sendEmail()).thenReturn(new GetEmailServiceDTO(false));

        when(transactionAuthServiceClient.requestAuthorization()).thenReturn(new GetTransactionAuthDTO("Não autorizado"));

        // then
        verify(payer, times(0)).deposit(value);
        verify(payee, times(0)).withdraw(value);

        verify(transactionRepository, times(0)).save(transaction);

        verify(emailServiceClient, times(0)).sendEmail();

        Exception thrown = assertThrows(Exception.class, () -> {
            PostTransactionDTO transactionData = new PostTransactionDTO(value, payerID, payeeID);
            TransactionReturnDTO transactionReturn = transactionService.makeTransaction(transactionData);

            validators.forEach(validator -> {
                verify(validator, times(1)).validate(transactionData);
            });

            assertNull(transactionReturn);
        });

        assertEquals("Transaction not authorized", thrown.getMessage());
    }

    @Test
    @DisplayName("Should pass if email is not sent")
    void makeTransactionCase3() {
        // given
        String payerID = UUID.randomUUID().toString();
        String payeeID = UUID.randomUUID().toString();

        User payer = mock(User.class);
        User payee = mock(User.class);

        int value = 100_00;

        Transaction transaction = new Transaction(null, value, payer, payee, LocalDateTime.now());

        TransactionReturnDTO expectedReturn = new TransactionReturnDTO(payerID, payeeID, value, false);

        // when
        when(payee.getId()).thenReturn(payeeID);
        when(payer.getId()).thenReturn(payerID);

        when(userService.findOne(payerID)).thenReturn(payer);
        when(userService.findOne(payeeID)).thenReturn(payee);

        when(transactionAuthServiceClient.requestAuthorization()).thenReturn(new GetTransactionAuthDTO("Autorizado"));

        when(emailServiceClient.sendEmail()).thenReturn(new GetEmailServiceDTO(false));

        PostTransactionDTO transactionData = new PostTransactionDTO(value, payerID, payeeID);
        TransactionReturnDTO transactionReturn = transactionService.makeTransaction(transactionData);

        // then
        verify(payer, times(1)).withdraw(value);
        verify(payee, times(1)).deposit(value);

        validators.forEach(validator -> {
            verify(validator, times(1)).validate(transactionData);
        });

        verify(transactionRepository, times(1)).save(transaction);

        verify(emailServiceClient, times(1)).sendEmail();

        assertThat(transactionReturn).isEqualTo(expectedReturn);
    }


}