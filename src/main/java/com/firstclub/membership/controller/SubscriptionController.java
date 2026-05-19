package com.firstclub.membership.controller;

import com.firstclub.membership.dto.SubscriptionRequest;
import com.firstclub.membership.dto.SubscriptionResponse;
import com.firstclub.membership.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionResponse> subscribe(@RequestBody SubscriptionRequest request) {
        return ResponseEntity.ok(subscriptionService.subscribe(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SubscriptionResponse> getSubscription(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getActiveSubscription(userId));
    }

    @PutMapping("/{userId}/cancel")
    public ResponseEntity<SubscriptionResponse> cancelSubscription(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.cancelSubscription(userId));
    }
}
