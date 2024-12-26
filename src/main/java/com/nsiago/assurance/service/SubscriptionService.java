package com.nsiago.assurance.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.nsiago.assurance.domain.Subscription;
import com.nsiago.assurance.dto.SubscriptionRequest;
import com.nsiago.assurance.dto.SubscriptionResponse;
import com.nsiago.assurance.repository.SimulationRepository;
import com.nsiago.assurance.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    
    private final SimulationRepository simulationRepository;
    private final SubscriptionRepository subscriptionRepository;
    
    @Transactional(readOnly = true)
    public SubscriptionResponse getSubscription(Long id) {
        var subscription = subscriptionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Souscription non trouvée"));
        return mapToResponse(subscription);
    }
    
    @Transactional(readOnly = true)
    public String getSubscriptionStatus(Long id) {
        var subscription = subscriptionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Souscription non trouvée"));
        return subscription.getStatus();
    }
    
    @Transactional(readOnly = true)
    public byte[] generateAttestation(Long id) {
        var subscription = subscriptionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Souscription non trouvée"));
            
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            // En-tête
            Paragraph header = new Paragraph("ATTESTATION D'ASSURANCE AUTOMOBILE")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(16);
            document.add(header);
            
            // Informations de l'attestation
            document.add(new Paragraph("\nN° Attestation: " + subscription.getAttestationNumber()));
            document.add(new Paragraph("Date de souscription: " + 
                subscription.getSubscriptionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            
            // Informations du véhicule
            document.add(new Paragraph("\nINFORMATIONS DU VÉHICULE").setBold());
            Table vehicleTable = new Table(2);
            vehicleTable.addCell("Immatriculation");
            vehicleTable.addCell(subscription.getRegistrationNumber());
            vehicleTable.addCell("Catégorie");
            vehicleTable.addCell(subscription.getSimulation().getVehicleCategory().getLibelle());
            vehicleTable.addCell("Première mise en circulation");
            vehicleTable.addCell(subscription.getFirstRegistrationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            document.add(vehicleTable);
            
            // Informations du souscripteur
            document.add(new Paragraph("\nINFORMATIONS DU SOUSCRIPTEUR").setBold());
            Table subscriberTable = new Table(2);
            subscriberTable.addCell("Nom");
            subscriberTable.addCell(subscription.getSubscriberLastName());
            subscriberTable.addCell("Prénom");
            subscriberTable.addCell(subscription.getSubscriberFirstName());
            subscriberTable.addCell("Adresse");
            subscriberTable.addCell(subscription.getSubscriberAddress());
            subscriberTable.addCell("Ville");
            subscriberTable.addCell(subscription.getSubscriberCity());
            subscriberTable.addCell("Téléphone");
            subscriberTable.addCell(subscription.getSubscriberPhone());
            document.add(subscriberTable);
            
            // Informations du produit
            document.add(new Paragraph("\nINFORMATIONS DU PRODUIT").setBold());
            Table productTable = new Table(2);
            productTable.addCell("Produit");
            productTable.addCell(subscription.getSimulation().getProduct().getName());
            productTable.addCell("Prime annuelle");
            productTable.addCell(subscription.getSimulation().getPrice().toString() + " F CFA");
            document.add(productTable);
            
            // Pied de page
            document.add(new Paragraph("\nCette attestation est valable pour une durée d'un an à compter de la date de souscription.")
                .setItalic());
            
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération de l'attestation", e);
        }
    }
    
    @Transactional
    public SubscriptionResponse createSubscription(SubscriptionRequest request) {
        var simulation = simulationRepository.findByQuoteReference(request.getQuoteReference())
            .orElseThrow(() -> new RuntimeException("Simulation non trouvée"));
            
        validateSimulationValidity(simulation.getEndDate().toLocalDate());
        
        var subscription = new Subscription();
        subscription.setSimulation(simulation);
        subscription.setRegistrationNumber(request.getRegistrationNumber());
        subscription.setColor(request.getColor());
        subscription.setNumberOfSeats(request.getNumberOfSeats());
        subscription.setNumberOfDoors(request.getNumberOfDoors());
        subscription.setFirstRegistrationDate(request.getFirstRegistrationDate());
        subscription.setSubscriberFirstName(request.getSubscriberFirstName());
        subscription.setSubscriberLastName(request.getSubscriberLastName());
        subscription.setSubscriberAddress(request.getSubscriberAddress());
        subscription.setSubscriberPhone(request.getSubscriberPhone());
        subscription.setSubscriberIdNumber(request.getSubscriberIdNumber());
        subscription.setSubscriberCity(request.getSubscriberCity());
        subscription.setSubscriptionDate(LocalDate.now());
        subscription.setStatus("EN_COURS");
        subscription.setAttestationNumber("AT" + UUID.randomUUID().toString().substring(0, 12).toUpperCase());
        
        subscription = subscriptionRepository.save(subscription);
        
        return mapToResponse(subscription);
    }
    
    private void validateSimulationValidity(LocalDate endDate) {
        if (LocalDate.now().isAfter(endDate)) {
            throw new RuntimeException("La simulation a expiré");
        }
    }
    
    private SubscriptionResponse mapToResponse(Subscription subscription) {
        var response = new SubscriptionResponse();
        response.setId(subscription.getId());
        response.setQuoteReference(subscription.getSimulation().getQuoteReference());
        response.setProductName(subscription.getSimulation().getProduct().getName());
        response.setPrice(subscription.getSimulation().getPrice());
        response.setRegistrationNumber(subscription.getRegistrationNumber());
        response.setColor(subscription.getColor());
        response.setNumberOfSeats(subscription.getNumberOfSeats());
        response.setNumberOfDoors(subscription.getNumberOfDoors());
        response.setFirstRegistrationDate(subscription.getFirstRegistrationDate());
        response.setSubscriberFirstName(subscription.getSubscriberFirstName());
        response.setSubscriberLastName(subscription.getSubscriberLastName());
        response.setSubscriberAddress(subscription.getSubscriberAddress());
        response.setSubscriberPhone(subscription.getSubscriberPhone());
        response.setSubscriberIdNumber(subscription.getSubscriberIdNumber());
        response.setSubscriberCity(subscription.getSubscriberCity());
        response.setSubscriptionDate(subscription.getSubscriptionDate());
        response.setStatus(subscription.getStatus());
        response.setAttestationNumber(subscription.getAttestationNumber());
        return response;
    }
    
    @Transactional(readOnly = true)
    public List<SubscriptionResponse> getAllSubscriptions() {
        return subscriptionRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }
} 