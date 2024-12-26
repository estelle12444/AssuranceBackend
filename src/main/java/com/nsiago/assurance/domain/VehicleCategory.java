package com.nsiago.assurance.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vehicle_categories")
public class VehicleCategory {
    
    @Id
    private String code;
    
    @Column(nullable = false)
    private String libelle;
    
    @Column(nullable = false)
    private String description;
} 