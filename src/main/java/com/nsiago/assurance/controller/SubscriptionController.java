package com.nsiago.assurance.controller;

import com.nsiago.assurance.dto.SubscriptionRequest;
import com.nsiago.assurance.dto.SubscriptionResponse;
import com.nsiago.assurance.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Souscriptions", description = "API de gestion des souscriptions d'assurance")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SubscriptionController {
    
    private final SubscriptionService subscriptionService;
    
    @GetMapping
    @Operation(summary = "Lister les souscriptions", description = "Récupère toutes les souscriptions")
    public ResponseEntity<List<SubscriptionResponse>> getAllSubscriptions() {
        try {
            log.info("Récupération de toutes les souscriptions");
            return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des souscriptions", e);
            throw e;
        }
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Créer une souscription", description = "Crée une nouvelle souscription d'assurance")
    public ResponseEntity<SubscriptionResponse> createSubscription(@Valid @RequestBody SubscriptionRequest request) {
        try {
            log.info("Création d'une nouvelle souscription: {}", request);
            SubscriptionResponse response = subscriptionService.createSubscription(request);
            log.info("Souscription créée avec succès: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la création de la souscription", e);
            throw e;
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une souscription", description = "Récupère une souscription par son identifiant")
    public ResponseEntity<SubscriptionResponse> getSubscription(@PathVariable Long id) {
        try {
            log.info("Récupération de la souscription avec l'ID: {}", id);
            return ResponseEntity.ok(subscriptionService.getSubscription(id));
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de la souscription {}", id, e);
            throw e;
        }
    }
    
    @GetMapping("/status/{id}")
    @Operation(summary = "Récupérer le statut d'une souscription", description = "Récupère le statut d'une souscription")
    public ResponseEntity<String> getSubscriptionStatus(@PathVariable Long id) {
        try {
            log.info("Récupération du statut de la souscription avec l'ID: {}", id);
            return ResponseEntity.ok(subscriptionService.getSubscriptionStatus(id));
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du statut de la souscription {}", id, e);
            throw e;
        }
    }
    
    @GetMapping("/{id}/attestation")
    @Operation(summary = "Générer l'attestation", description = "Génère l'attestation d'assurance au format PDF")
    public ResponseEntity<byte[]> generateAttestation(@PathVariable Long id) {
        try {
            log.info("Génération de l'attestation pour la souscription avec l'ID: {}", id);
            byte[] pdfContent = subscriptionService.generateAttestation(id);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "attestation.pdf");
            headers.setContentLength(pdfContent.length);
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
        } catch (Exception e) {
            log.error("Erreur lors de la génération de l'attestation pour la souscription {}", id, e);
            throw e;
        }
    }
} 