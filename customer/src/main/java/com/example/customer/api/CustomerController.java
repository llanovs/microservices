package com.example.customer.api;

import com.example.customer.model.Customer;
import com.example.customer.model.CustomerRegistrationRequest;
import com.example.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        log.info("new customer registration {}", request);
        customerService.registerCustomer(request);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getList();
    }
}
