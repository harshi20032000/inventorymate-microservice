package com.harshi_solution.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.harshi_solution.order.dto.CreatePaymentRequest;
import com.harshi_solution.order.dto.BaseUIResponse;
import com.harshi_solution.order.dto.PaymentResponseDTO;

@FeignClient(name = "payments-client", url = "payments.service.url")
public interface PaymentClient {

    @PostMapping("/api/v1/payments")
    BaseUIResponse<PaymentResponseDTO> createPayment(
            @RequestBody CreatePaymentRequest request);
}
