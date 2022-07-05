package com.example.customer.service;

import com.example.amqp.RabbitMqMessageProducer;
import com.example.clients.client.FraudClient;
import com.example.clients.model.FraudCheckResponse;
import com.example.clients.model.NotificationRequest;
import com.example.customer.model.Customer;
import com.example.customer.model.CustomerRegistrationRequest;
import com.example.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final FraudClient client;
    private final RabbitMqMessageProducer rabbitMqMessageProducer;


    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        repository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse = client.isFraudster(customer.getId());
        if (fraudCheckResponse != null && fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }


        NotificationRequest notificationRequest = new NotificationRequest(customer.getId(),
                customer.getEmail(), String.format("Hi %s, welcome to CustomerService", customer.getFirstName()));
        rabbitMqMessageProducer.publish(notificationRequest, "internal.exchange",
                "internal.notification.routing-key");
    }

    public List<Customer> getList() {
        return repository.findAll();
    }
}
