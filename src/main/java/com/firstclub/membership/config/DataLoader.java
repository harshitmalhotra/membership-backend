package com.firstclub.membership.config;

import com.firstclub.membership.entity.*;
import com.firstclub.membership.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final MembershipPlanRepository planRepository;
    private final MembershipTierRepository tierRepository;
    private final BenefitRepository benefitRepository;
    private final TierBenefitRepository tierBenefitRepository;

    @Override
    public void run(String... args) {
        loadData();
    }

    private void loadData() {
        if (userRepository.count() > 0) return; // already loaded

        // 1. Users
        User testUser = userRepository.save(User.builder().name("John Doe").email("john@example.com").build());

        // 2. Plans
        MembershipPlan monthly = planRepository.save(MembershipPlan.builder().name("Monthly Plan").duration(PlanDuration.MONTHLY).basePrice(new BigDecimal("9.99")).build());
        MembershipPlan yearly = planRepository.save(MembershipPlan.builder().name("Yearly Plan").duration(PlanDuration.YEARLY).basePrice(new BigDecimal("99.99")).build());

        // 3. Tiers
        MembershipTier silver = tierRepository.save(MembershipTier.builder().name("SILVER").rank(1).minOrdersRequired(0).minSpendRequired(0.0).build());
        MembershipTier gold = tierRepository.save(MembershipTier.builder().name("GOLD").rank(2).minOrdersRequired(5).minSpendRequired(100.0).build());
        MembershipTier platinum = tierRepository.save(MembershipTier.builder().name("PLATINUM").rank(3).minOrdersRequired(15).minSpendRequired(500.0).build());

        // 4. Benefits
        Benefit freeDelivery = benefitRepository.save(Benefit.builder().code("FREE_DELIVERY").description("Free delivery on eligible orders").build());
        Benefit extraDiscount = benefitRepository.save(Benefit.builder().code("EXTRA_DISCOUNT").description("Extra % discount on items").build());
        Benefit earlyAccess = benefitRepository.save(Benefit.builder().code("EARLY_ACCESS").description("Early access to sales").build());
        Benefit prioritySupport = benefitRepository.save(Benefit.builder().code("PRIORITY_SUPPORT").description("Priority support for premium members").build());

        // 5. TierBenefits
        // Silver gets 5% discount
        tierBenefitRepository.save(TierBenefit.builder().tier(silver).benefit(extraDiscount).configurationValue("5").build());
        
        // Gold gets free delivery + 10% discount
        tierBenefitRepository.save(TierBenefit.builder().tier(gold).benefit(freeDelivery).configurationValue("true").build());
        tierBenefitRepository.save(TierBenefit.builder().tier(gold).benefit(extraDiscount).configurationValue("10").build());

        // Platinum gets all
        tierBenefitRepository.save(TierBenefit.builder().tier(platinum).benefit(freeDelivery).configurationValue("true").build());
        tierBenefitRepository.save(TierBenefit.builder().tier(platinum).benefit(extraDiscount).configurationValue("15").build());
        tierBenefitRepository.save(TierBenefit.builder().tier(platinum).benefit(earlyAccess).configurationValue("true").build());
        tierBenefitRepository.save(TierBenefit.builder().tier(platinum).benefit(prioritySupport).configurationValue("true").build());

        System.out.println("Data Loaded Successfully! Test User ID: " + testUser.getId());
        System.out.println("Monthly Plan ID: " + monthly.getId() + ", Silver Tier ID: " + silver.getId());
    }
}
