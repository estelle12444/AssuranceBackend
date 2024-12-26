package com.nsiago.assurance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;

@Data
public class SubscriptionRequest {
    
    @NotBlank(message = "La référence du devis est obligatoire")
    private String quoteReference;
    
    @NotBlank(message = "Le numéro d'immatriculation est obligatoire")
    private String registrationNumber;
    
    @NotBlank(message = "La couleur est obligatoire")
    private String color;
    
    @NotNull(message = "Le nombre de sièges est obligatoire")
    @Positive(message = "Le nombre de sièges doit être positif")
    private Integer numberOfSeats;
    
    @NotNull(message = "Le nombre de portes est obligatoire")
    @Positive(message = "Le nombre de portes doit être positif")
    private Integer numberOfDoors;
    
    @NotNull(message = "La date de première mise en circulation est obligatoire")
    @Past(message = "La date de première mise en circulation doit être dans le passé")
    private LocalDate firstRegistrationDate;
    
    @NotBlank(message = "Le prénom est obligatoire")
    private String subscriberFirstName;
    
    @NotBlank(message = "Le nom est obligatoire")
    private String subscriberLastName;
    
    @NotBlank(message = "L'adresse est obligatoire")
    private String subscriberAddress;
    
    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    private String subscriberPhone;
    
    @NotBlank(message = "Le numéro de carte d'identité est obligatoire")
    private String subscriberIdNumber;
    
    @NotBlank(message = "La ville est obligatoire")
    private String subscriberCity;
} 