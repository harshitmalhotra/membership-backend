package com.firstclub.membership.dto;

import com.firstclub.membership.entity.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {
    private Long id;
    private Long userId;
    private String planName;
    private String tierName;
    private SubscriptionStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
}
