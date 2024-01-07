package com.picpay.challenge.domain.user.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PostUserDTO(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        @Pattern(regexp = "[0-9]{11}")
        String cpf,

        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
