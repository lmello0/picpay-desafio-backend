package com.picpay.challenge.DTO.user;

import com.picpay.challenge.domain.user.Type;
import com.picpay.challenge.domain.user.User;

public record GetUserDTO(
        String id,
        String firstName,
        String lastName,
        String document,
        String email,
        Type type,
        int wallet,
        boolean isActive
) {
    public GetUserDTO(User data) {
        this(
                data.getId(),
                data.getFirstName(),
                data.getLastName(),
                data.getDocument(),
                data.getEmail(),
                data.getType(),
                data.getWallet(),
                data.isActive()
        );
    }
}
