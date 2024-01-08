package com.picpay.challenge.domain.user.security;

public record AuthData(
        String email,
        String password
) {
}
