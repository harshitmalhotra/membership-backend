package com.firstclub.membership.service;

import com.firstclub.membership.entity.MembershipTier;
import com.firstclub.membership.entity.Subscription;
import com.firstclub.membership.entity.SubscriptionStatus;
import com.firstclub.membership.entity.UserMetrics;
import com.firstclub.membership.repository.MembershipTierRepository;
import com.firstclub.membership.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TierEvaluationService {

    private final SubscriptionRepository subscriptionRepository;
    private final MembershipTierRepository tierRepository;

    @Transactional
    public void evaluateTierForUser(UserMetrics metrics) {
        Long userId = metrics.getUser().getId();
        Optional<Subscription> activeSubOpt = subscriptionRepository.findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE);
        
        if (activeSubOpt.isEmpty()) {
            log.info("No active subscription for user {}. Skipping tier evaluation.", userId);
            return;
        }

        Subscription subscription = activeSubOpt.get();
        MembershipTier currentTier = subscription.getTier();
        
        // Find if user qualifies for the NEXT tier
        Optional<MembershipTier> nextTierOpt = tierRepository.findFirstByRankGreaterThanOrderByRankAsc(currentTier.getRank());
        
        if (nextTierOpt.isPresent()) {
            MembershipTier nextTier = nextTierOpt.get();
            boolean qualifiesForOrders = nextTier.getMinOrdersRequired() != null && metrics.getTotalOrders() >= nextTier.getMinOrdersRequired();
            boolean qualifiesForSpend = nextTier.getMinSpendRequired() != null && metrics.getTotalSpend().doubleValue() >= nextTier.getMinSpendRequired();
            
            if (qualifiesForOrders || qualifiesForSpend) {
                log.info("Upgrading user {} from tier {} to tier {}", userId, currentTier.getName(), nextTier.getName());
                subscription.setTier(nextTier);
                subscriptionRepository.save(subscription);
                
                // Note: In a real system, you might recursively check if they jumped multiple tiers.
                // For simplicity, we assume they progress one rank at a time, or we'd just query all tiers and pick the highest they qualify for.
            }
        }
    }
}
