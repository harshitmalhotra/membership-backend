package com.firstclub.membership.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TierResponse {
    private Long id;
    private String name;
    private Integer rank;
    private Integer minOrdersRequired;
    private Double minSpendRequired;
    private List<BenefitResponse> benefits;
}
