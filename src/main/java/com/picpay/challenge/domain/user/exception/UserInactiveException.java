package com.picpay.challenge.domain.user.exception;

public class UserInactiveException extends RuntimeException {
    public UserInactiveException(String id) {
        super("User " + id + " is inactive");
    }
}
