package com.picpay.challenge.domain.transaction.DTO;

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
