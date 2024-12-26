package com.nsiago.assurance.repository;

import com.nsiago.assurance.domain.InsuranceProduct;
import com.nsiago.assurance.domain.VehicleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InsuranceProductRepository extends JpaRepository<InsuranceProduct, Long> {
    Optional<InsuranceProduct> findByName(String name);
    
    @Query("SELECT p FROM InsuranceProduct p JOIN p.eligibleCategories c WHERE c = :category")
    List<InsuranceProduct> findByEligibleCategory(VehicleCategory category);
} 