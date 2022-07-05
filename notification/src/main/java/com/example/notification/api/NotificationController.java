package com.example.notification.api;

import com.example.notification.model.NotificationRequest;
import com.example.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/notification")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public void sendNotification(@RequestBody NotificationRequest request) {
        log.info("New notification {}", request);
        service.send(request);
    }

}
