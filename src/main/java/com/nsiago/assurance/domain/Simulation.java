package com.nsiago.assurance.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "simulations")
public class Simulation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String quoteReference;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private InsuranceProduct product;
    
    @Column(nullable = false)
    private LocalDateTime creationDate;
    
    @Column(nullable = false)
    private LocalDateTime endDate;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(nullable = false)
    private Integer vehicleAge;
    
    @Column(nullable = false)
    private BigDecimal vehicleValue;
    
    @ManyToOne
    @JoinColumn(name = "category_code", nullable = false)
    private VehicleCategory vehicleCategory;
    
    @Column(nullable = false)
    private Integer fiscalPower;
} 