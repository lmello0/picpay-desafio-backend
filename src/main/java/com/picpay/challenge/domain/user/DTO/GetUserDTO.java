package com.picpay.challenge.domain.user.DTO;

import com.picpay.challenge.domain.user.User;

public record GetUserDTO(
        String id,
        String firstName,
        String lastName,
        String cpf,
        String email,
        boolean isActive
) {
    public GetUserDTO(User data) {
        this(
                data.getId(),
                data.getFirstName(),
                data.getLastName(),
                data.getCpf(),
                data.getEmail(),
                data.isActive()
        );
    }
}
