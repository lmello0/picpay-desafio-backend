package com.picpay.challenge.domain.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super("User " + id + " doesn't exists");
    }
}
