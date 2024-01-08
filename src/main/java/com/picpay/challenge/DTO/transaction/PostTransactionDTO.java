package com.picpay.challenge.DTO.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostTransactionDTO(
        @NotNull
        int value,

        @NotBlank
        String payer,

        @NotBlank
        String payee
) {
}
