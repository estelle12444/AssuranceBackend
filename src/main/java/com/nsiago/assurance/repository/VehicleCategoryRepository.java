package com.nsiago.assurance.repository;

import com.nsiago.assurance.domain.VehicleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleCategoryRepository extends JpaRepository<VehicleCategory, String> {
} 