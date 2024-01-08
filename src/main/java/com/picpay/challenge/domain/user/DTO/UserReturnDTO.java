package com.picpay.challenge.domain.user.DTO;

import com.picpay.challenge.domain.user.User;

public record UserReturnDTO(
        String id,
        String firstName,
        String lastName,
        String document,
        int wallet
) {
    public UserReturnDTO(User data) {
        this(
                data.getId(),
                data.getFirstName(),
                data.getLastName(),
                data.getDocument(),
                data.getWallet()
        );
    }
}
