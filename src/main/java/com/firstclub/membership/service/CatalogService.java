package com.firstclub.membership.service;

import com.firstclub.membership.dto.BenefitResponse;
import com.firstclub.membership.dto.PlanResponse;
import com.firstclub.membership.dto.TierResponse;
import com.firstclub.membership.repository.MembershipPlanRepository;
import com.firstclub.membership.repository.MembershipTierRepository;
import com.firstclub.membership.repository.TierBenefitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final MembershipPlanRepository planRepository;
    private final MembershipTierRepository tierRepository;
    private final TierBenefitRepository tierBenefitRepository;

    public List<PlanResponse> getAllPlans() {
        return planRepository.findAll().stream()
                .map(plan -> PlanResponse.builder()
                        .id(plan.getId())
                        .name(plan.getName())
                        .duration(plan.getDuration())
                        .basePrice(plan.getBasePrice())
                        .build())
                .collect(Collectors.toList());
    }

    public List<TierResponse> getAllTiers() {
        return tierRepository.findAll().stream()
                .map(tier -> {
                    List<BenefitResponse> benefits = tierBenefitRepository.findByTierId(tier.getId()).stream()
                            .map(tb -> BenefitResponse.builder()
                                    .code(tb.getBenefit().getCode())
                                    .description(tb.getBenefit().getDescription())
                                    .configurationValue(tb.getConfigurationValue())
                                    .build())
                            .collect(Collectors.toList());

                    return TierResponse.builder()
                            .id(tier.getId())
                            .name(tier.getName())
                            .rank(tier.getRank())
                            .minOrdersRequired(tier.getMinOrdersRequired())
                            .minSpendRequired(tier.getMinSpendRequired())
                            .benefits(benefits)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
