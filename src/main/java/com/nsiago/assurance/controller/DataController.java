package com.nsiago.assurance.controller;

import com.nsiago.assurance.domain.*;
import com.nsiago.assurance.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {

    private final VehicleCategoryRepository vehicleCategoryRepository;
    private final FiscalPowerRepository fiscalPowerRepository;
    private final GuaranteeRepository guaranteeRepository;
    private final InsuranceProductRepository insuranceProductRepository;
    private final SimulationRepository simulationRepository;
    private final SubscriptionRepository subscriptionRepository;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllData() {
        Map<String, Object> data = Map.of(
            "categories", vehicleCategoryRepository.findAll(),
            "fiscalPowers", fiscalPowerRepository.findAll(),
            "guarantees", guaranteeRepository.findAll(),
            "products", insuranceProductRepository.findAll(),
            "simulations", simulationRepository.findAll(),
            "subscriptions", subscriptionRepository.findAll()
        );
        return ResponseEntity.ok(data);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<VehicleCategory>> getCategories() {
        return ResponseEntity.ok(vehicleCategoryRepository.findAll());
    }

    @GetMapping("/fiscal-powers")
    public ResponseEntity<List<FiscalPower>> getFiscalPowers() {
        return ResponseEntity.ok(fiscalPowerRepository.findAll());
    }

    @GetMapping("/guarantees")
    public ResponseEntity<List<Guarantee>> getGuarantees() {
        return ResponseEntity.ok(guaranteeRepository.findAll());
    }

    @GetMapping("/products")
    public ResponseEntity<List<InsuranceProduct>> getProducts() {
        return ResponseEntity.ok(insuranceProductRepository.findAll());
    }

    @GetMapping("/simulations")
    public ResponseEntity<List<Simulation>> getSimulations() {
        return ResponseEntity.ok(simulationRepository.findAll());
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<List<Subscription>> getSubscriptions() {
        return ResponseEntity.ok(subscriptionRepository.findAll());
    }
} 