package com.firstclub.membership.controller;

import com.firstclub.membership.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final ApplicationEventPublisher eventPublisher;

    @PostMapping("/order")
    public ResponseEntity<String> recordOrder(@RequestBody OrderEvent event) {
        // Publish the event asynchronously to simulate an order being placed in a real system
        eventPublisher.publishEvent(event);
        return ResponseEntity.accepted().body("Order recorded and is being processed.");
    }
}
