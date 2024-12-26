package com.nsiago.assurance.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "guarantees")
public class Guarantee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal rate;
    
    private BigDecimal minimumPremium;
    
    private Integer maxVehicleAge;
    
    @Column(nullable = false)
    private Boolean isBasedOnNewValue;
    
    @Column(nullable = false)
    private Boolean isBasedOnVenalValue;
    
    private BigDecimal coveredValuePercentage;
} 