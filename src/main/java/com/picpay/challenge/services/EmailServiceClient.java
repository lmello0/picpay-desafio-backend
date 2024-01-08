package com.picpay.challenge.services;

import com.picpay.challenge.DTO.email.GetEmailServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "email-service", url = "${feign.client.config.email-service.url}")
public interface EmailServiceClient {
    @GetMapping
    GetEmailServiceDTO sendEmail();
}
