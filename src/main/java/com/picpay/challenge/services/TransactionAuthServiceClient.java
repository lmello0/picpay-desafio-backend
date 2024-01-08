package com.picpay.challenge.services;

import com.picpay.challenge.DTO.transactionAuth.GetTransactionAuthDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "transaction-auth-service", url= "${feign.client.config.transaction-auth-service.url}")
public interface TransactionAuthServiceClient {
    @GetMapping
    GetTransactionAuthDTO requestAuthorization();
}
