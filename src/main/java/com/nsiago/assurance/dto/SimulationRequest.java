package com.nsiago.assurance.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SimulationRequest {
    
    @NotNull(message = "Le produit d'assurance est obligatoire")
    private String productName;
    
    @NotNull(message = "La catégorie du véhicule est obligatoire")
    private String vehicleCategoryCode;
    
    @NotNull(message = "L'âge du véhicule est obligatoire")
    @Positive(message = "L'âge du véhicule doit être positif")
    private Integer vehicleAge;
    
    @NotNull(message = "La puissance fiscale est obligatoire")
    @Positive(message = "La puissance fiscale doit être positive")
    private Integer fiscalPower;
    
    @NotNull(message = "La valeur du véhicule est obligatoire")
    @Positive(message = "La valeur du véhicule doit être positive")
    private BigDecimal vehicleValue;
} 