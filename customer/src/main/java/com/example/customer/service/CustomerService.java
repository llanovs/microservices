package com.example.customer.service;

import com.example.amqp.RabbitMqMessageProducer;
import com.example.clients.client.FraudClient;
import com.example.clients.model.FraudCheckResponse;
import com.example.clients.model.NotificationRequest;
import com.example.customer.model.Customer;
import com.example.customer.model.CustomerRegistrationRequest;
import com.example.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.Span;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final FraudClient client;
    private final RabbitMqMessageProducer rabbitMqMessageProducer;
    private final Tracer tracer;
    private static final Logger log = Logger.getLogger(CustomerService.class.getName());


    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        repository.saveAndFlush(customer);
        log.log(Level.INFO, "Customer was added to the database");
        FraudCheckResponse fraudCheckResponse = client.isFraudster(customer.getId());
        if (fraudCheckResponse != null && fraudCheckResponse.isFraudster()) {
            log.log(Level.WARNING, "Customer didn't pass a check");
            throw new IllegalStateException("Fraudster");
        }

        Span span =  tracer.nextSpan().name("publishNotification");
        span.start();

        NotificationRequest notificationRequest = new NotificationRequest(customer.getId(),
                customer.getEmail(), String.format("Hi %s, welcome to CustomerService", customer.getFirstName()));
        span.tag("notificationRequest", notificationRequest.toString());
        rabbitMqMessageProducer.publish(notificationRequest, "internal.exchange",
                "internal.notification.routing-key");
        span.end();
        log.info("Customer was added to the rabbitmq");
    }

    public List<Customer> getList() {
        return repository.findAll();
    }
}
