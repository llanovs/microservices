package com.example.fraud.service;

import com.example.fraud.model.FraudCheckHistory;
import com.example.fraud.repository.FraudCheckRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class FraudCheckService {

    private final FraudCheckRepository repository;

    public boolean isFraudulentCustomer(Long customerId) {
        //todo: implement checking
        FraudCheckHistory checkHistory = FraudCheckHistory.builder()
                .isFraudster(false)
                .customerId(customerId)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(checkHistory);
        return false;
    }

}
