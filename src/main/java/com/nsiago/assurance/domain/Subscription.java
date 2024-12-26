package com.nsiago.assurance.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "subscriptions")
public class Subscription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "simulation_id", nullable = false)
    private Simulation simulation;
    
    @Column(nullable = false)
    private String registrationNumber;
    
    @Column(nullable = false)
    private String color;
    
    @Column(nullable = false)
    private Integer numberOfSeats;
    
    @Column(nullable = false)
    private Integer numberOfDoors;
    
    @Column(nullable = false)
    private LocalDate firstRegistrationDate;
    
    @Column(nullable = false)
    private String subscriberFirstName;
    
    @Column(nullable = false)
    private String subscriberLastName;
    
    @Column(nullable = false)
    private String subscriberAddress;
    
    @Column(nullable = false)
    private String subscriberPhone;
    
    @Column(nullable = false)
    private String subscriberIdNumber;
    
    @Column(nullable = false)
    private String subscriberCity;
    
    @Column(nullable = false)
    private LocalDate subscriptionDate;
    
    @Column(nullable = false)
    private String status;
    
    private String attestationNumber;
} 