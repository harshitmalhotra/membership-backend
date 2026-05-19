package com.firstclub.membership.service;

import com.firstclub.membership.entity.User;
import com.firstclub.membership.entity.UserMetrics;
import com.firstclub.membership.event.OrderEvent;
import com.firstclub.membership.repository.UserMetricsRepository;
import com.firstclub.membership.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserMetricsService {

    private final UserMetricsRepository metricsRepository;
    private final UserRepository userRepository;
    private final TierEvaluationService tierEvaluationService;

    @Async
    @EventListener
    @Transactional
    public void handleOrderEvent(OrderEvent event) {
        User user = userRepository.findById(event.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserMetrics metrics = metricsRepository.findByUserId(user.getId())
                .orElseGet(() -> UserMetrics.builder().user(user).build());

        metrics.setTotalOrders(metrics.getTotalOrders() + 1);
        metrics.setTotalSpend(metrics.getTotalSpend().add(event.getOrderTotal()));
        
        metrics = metricsRepository.save(metrics);

        // After updating metrics, trigger tier evaluation
        tierEvaluationService.evaluateTierForUser(metrics);
    }
}
