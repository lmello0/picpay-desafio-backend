package com.picpay.challenge.domain.transaction.exception;

public class NotEnoughMoneyException extends InvalidTransactionException {
    public NotEnoughMoneyException() {
        super("Not enough money to make transaction");
    }
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
