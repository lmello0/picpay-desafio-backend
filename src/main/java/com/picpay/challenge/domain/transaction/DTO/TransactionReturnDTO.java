package com.picpay.challenge.domain.transaction.DTO;

public record TransactionReturnDTO(
        String payer,
        String payee,
        int value
) {
}
