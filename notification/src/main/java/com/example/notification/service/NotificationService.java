package com.example.notification.service;

import com.example.notification.model.NotificationRequest;
import com.example.notification.model.Notification;
import com.example.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    public void send(NotificationRequest request) {
        Notification notification = Notification.builder()
                .customerEmail(request.getToCustomerEmail())
                .customerId(request.getToCustomerId())
                .sender("Test")
                .msg(request.getMessage())
                .sentAt(LocalDateTime.now())
                .build();
        repository.save(notification);
    }
}
