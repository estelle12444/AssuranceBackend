package com.nsiago.assurance.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SubscriptionResponse {
    private Long id;
    private String quoteReference;
    private String productName;
    private BigDecimal price;
    private String registrationNumber;
    private String color;
    private Integer numberOfSeats;
    private Integer numberOfDoors;
    private LocalDate firstRegistrationDate;
    private String subscriberFirstName;
    private String subscriberLastName;
    private String subscriberAddress;
    private String subscriberPhone;
    private String subscriberIdNumber;
    private String subscriberCity;
    private LocalDate subscriptionDate;
    private String status;
    private String attestationNumber;
} 