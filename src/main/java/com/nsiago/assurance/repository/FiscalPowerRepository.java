package com.nsiago.assurance.repository;

import com.nsiago.assurance.domain.FiscalPower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FiscalPowerRepository extends JpaRepository<FiscalPower, Long> {
    
    @Query("SELECT fp FROM FiscalPower fp WHERE :power BETWEEN fp.minPower AND fp.maxPower")
    Optional<FiscalPower> findByPower(Integer power);
} 