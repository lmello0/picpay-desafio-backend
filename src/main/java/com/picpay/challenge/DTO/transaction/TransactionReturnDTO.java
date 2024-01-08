package com.picpay.challenge.DTO.transaction;

public record TransactionReturnDTO(
        String payer,
        String payee,
        int value,
        String emailSent
) {
}
