package com.picpay.challenge.domain.user.DTO;

import com.picpay.challenge.domain.user.User;

public record UserReturnDTO(
        String id,
        String firstName,
        String lastName,
        String cpf
) {
    public UserReturnDTO(User data) {
        this(
                data.getId(),
                data.getFirstName(),
                data.getLastName(),
                data.getCpf()
        );
    }
}
