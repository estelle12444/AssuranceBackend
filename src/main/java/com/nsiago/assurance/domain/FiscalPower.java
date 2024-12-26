package com.nsiago.assurance.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "fiscal_powers")
public class FiscalPower {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Integer minPower;
    
    @Column(nullable = false)
    private Integer maxPower;
    
    @Column(nullable = false)
    private BigDecimal premium;
} 