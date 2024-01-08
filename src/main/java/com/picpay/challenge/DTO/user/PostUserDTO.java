package com.picpay.challenge.DTO.user;

import com.picpay.challenge.domain.user.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PostUserDTO(
        @NotBlank
        String firstName,
        String lastName,

        @NotBlank
        @Pattern(regexp = "[0-9]{11}|[0-9]{14}")
        String document,

        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotNull
        Type type
) {
}
