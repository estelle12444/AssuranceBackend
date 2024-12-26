package com.nsiago.assurance.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SimulationResponse {
    private String quoteReference;
    private String productName;
    private String vehicleCategoryCode;
    private Integer vehicleAge;
    private Integer fiscalPower;
    private BigDecimal vehicleValue;
    private BigDecimal price;
    private LocalDateTime creationDate;
    private LocalDateTime endDate;
} 