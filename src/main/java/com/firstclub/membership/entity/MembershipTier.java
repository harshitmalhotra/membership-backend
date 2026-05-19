package com.firstclub.membership.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "membership_tiers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipTier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g., SILVER, GOLD, PLATINUM

    // Higher rank means better tier (e.g., 1=Silver, 2=Gold, 3=Platinum)
    private Integer rank;
    
    // Criteria to reach this tier
    private Integer minOrdersRequired;
    private Double minSpendRequired;
}
