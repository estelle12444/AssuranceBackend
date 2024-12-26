package com.nsiago.assurance.service;

import com.nsiago.assurance.domain.*;
import com.nsiago.assurance.dto.SimulationRequest;
import com.nsiago.assurance.dto.SimulationResponse;
import com.nsiago.assurance.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SimulationService {
    
    private final InsuranceProductRepository productRepository;
    private final VehicleCategoryRepository categoryRepository;
    private final FiscalPowerRepository fiscalPowerRepository;
    private final SimulationRepository simulationRepository;
    
    @Transactional(readOnly = true)
    public SimulationResponse getSimulation(String quoteReference) {
        var simulation = simulationRepository.findByQuoteReference(quoteReference)
            .orElseThrow(() -> new RuntimeException("Simulation non trouvée"));
        return mapToResponse(simulation);
    }
    
    @Transactional
    public SimulationResponse createSimulation(SimulationRequest request) {
        var product = productRepository.findByName(request.getProductName())
            .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
            
        var category = categoryRepository.findById(request.getVehicleCategoryCode())
            .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
            
        var fiscalPower = fiscalPowerRepository.findByPower(request.getFiscalPower())
            .orElseThrow(() -> new RuntimeException("Puissance fiscale non trouvée"));
            
        validateProductCategory(product, category);
        
        BigDecimal totalPremium = calculateTotalPremium(product, request.getVehicleValue(), 
            request.getVehicleAge(), fiscalPower.getPremium());
            
        var simulation = new Simulation();
        simulation.setQuoteReference("QT" + UUID.randomUUID().toString().substring(0, 12).toUpperCase());
        simulation.setProduct(product);
        simulation.setVehicleCategory(category);
        simulation.setVehicleAge(request.getVehicleAge());
        simulation.setVehicleValue(request.getVehicleValue());
        simulation.setFiscalPower(request.getFiscalPower());
        simulation.setPrice(totalPremium);
        simulation.setCreationDate(LocalDateTime.now());
        simulation.setEndDate(LocalDateTime.now().plusWeeks(2));
        
        simulation = simulationRepository.save(simulation);
        
        return mapToResponse(simulation);
    }
    
    private void validateProductCategory(InsuranceProduct product, VehicleCategory category) {
        if (!product.getEligibleCategories().contains(category)) {
            throw new RuntimeException("La catégorie du véhicule n'est pas éligible pour ce produit");
        }
    }
    
    private BigDecimal calculateTotalPremium(InsuranceProduct product, BigDecimal vehicleValue, 
        Integer vehicleAge, BigDecimal basePremium) {
        BigDecimal totalPremium = basePremium;
        
        for (Guarantee guarantee : product.getGuarantees()) {
            if (!isEligibleForGuarantee(guarantee, vehicleAge)) {
                throw new RuntimeException("Le véhicule est trop ancien pour la garantie " + guarantee.getName());
            }
            
            BigDecimal guaranteePremium = calculateGuaranteePremium(guarantee, vehicleValue);
            totalPremium = totalPremium.add(guaranteePremium);
        }
        
        return totalPremium;
    }
    
    private boolean isEligibleForGuarantee(Guarantee guarantee, Integer vehicleAge) {
        return guarantee.getMaxVehicleAge() == null || vehicleAge <= guarantee.getMaxVehicleAge();
    }
    
    private BigDecimal calculateGuaranteePremium(Guarantee guarantee, BigDecimal vehicleValue) {
        BigDecimal premium;
        if (guarantee.getIsBasedOnNewValue()) {
            premium = vehicleValue.multiply(guarantee.getRate().divide(BigDecimal.valueOf(100)));
        } else if (guarantee.getIsBasedOnVenalValue()) {
            BigDecimal venalValue = vehicleValue;
            if (guarantee.getCoveredValuePercentage() != null) {
                venalValue = venalValue.multiply(guarantee.getCoveredValuePercentage().divide(BigDecimal.valueOf(100)));
            }
            premium = venalValue.multiply(guarantee.getRate().divide(BigDecimal.valueOf(100)));
        } else {
            premium = BigDecimal.ZERO;
        }
        
        if (guarantee.getMinimumPremium() != null && premium.compareTo(guarantee.getMinimumPremium()) < 0) {
            premium = guarantee.getMinimumPremium();
        }
        
        return premium;
    }
    
    private SimulationResponse mapToResponse(Simulation simulation) {
        var response = new SimulationResponse();
        response.setQuoteReference(simulation.getQuoteReference());
        response.setProductName(simulation.getProduct().getName());
        response.setVehicleCategoryCode(simulation.getVehicleCategory().getCode());
        response.setVehicleAge(simulation.getVehicleAge());
        response.setFiscalPower(simulation.getFiscalPower());
        response.setVehicleValue(simulation.getVehicleValue());
        response.setPrice(simulation.getPrice());
        response.setCreationDate(simulation.getCreationDate());
        response.setEndDate(simulation.getEndDate());
        return response;
    }
} 