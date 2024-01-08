package com.picpay.challenge.DTO.user;

public record UpdateUserDTO(
    String firstName,
    String lastName,
    String document,
    String email
) {

}
