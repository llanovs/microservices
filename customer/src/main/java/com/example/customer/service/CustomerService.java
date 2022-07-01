package com.example.customer.service;

import com.example.customer.model.Customer;
import com.example.customer.model.CustomerRegistrationRequest;
import com.example.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    @Autowired
    private final CustomerRepository repository;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        repository.save(customer);
    }

    public List<Customer> getList() {
        return repository.findAll();
    }
}
