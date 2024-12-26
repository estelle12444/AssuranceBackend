package com.nsiago.assurance.controller;

import com.nsiago.assurance.dto.SimulationRequest;
import com.nsiago.assurance.dto.SimulationResponse;
import com.nsiago.assurance.service.SimulationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simulations")
@RequiredArgsConstructor
@Tag(name = "Simulations", description = "API de gestion des simulations d'assurance")
public class SimulationController {
    
    private final SimulationService simulationService;
    
    @PostMapping
    @Operation(summary = "Créer une simulation", description = "Crée une nouvelle simulation d'assurance")
    public ResponseEntity<SimulationResponse> createSimulation(@Valid @RequestBody SimulationRequest request) {
        return ResponseEntity.ok(simulationService.createSimulation(request));
    }
    
    @GetMapping("/{quoteReference}")
    @Operation(summary = "Récupérer une simulation", description = "Récupère une simulation par sa référence")
    public ResponseEntity<SimulationResponse> getSimulation(@PathVariable String quoteReference) {
        return ResponseEntity.ok(simulationService.getSimulation(quoteReference));
    }
} 