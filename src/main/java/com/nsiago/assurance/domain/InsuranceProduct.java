package com.nsiago.assurance.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "insurance_products")
public class InsuranceProduct {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @ManyToMany
    @JoinTable(
        name = "product_guarantees",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "guarantee_id")
    )
    private Set<Guarantee> guarantees;
    
    @ManyToMany
    @JoinTable(
        name = "product_eligible_categories",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_code")
    )
    private Set<VehicleCategory> eligibleCategories;
} 