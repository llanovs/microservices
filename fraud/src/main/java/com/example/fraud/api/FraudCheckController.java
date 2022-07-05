package com.example.fraud.api;

import com.example.clients.model.FraudCheckResponse;
import com.example.fraud.service.FraudCheckService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/fraud-check")
@AllArgsConstructor
public class FraudCheckController {

    private final FraudCheckService service;

    @GetMapping(path = "{customerId}")
    public FraudCheckResponse checkFraudCheckHistory(@PathVariable Long customerId) {
        boolean isFraudulentCustomer = service.isFraudulentCustomer(customerId);
        log.info("Fraud check request for customer {}", customerId);
        return new FraudCheckResponse(isFraudulentCustomer);
    }
}
