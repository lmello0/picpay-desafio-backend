package com.picpay.challenge.domain.user.DTO;

public record UpdateUserDTO(
    String firstName,
    String lastName,
    String cpf,
    String email
) {

}