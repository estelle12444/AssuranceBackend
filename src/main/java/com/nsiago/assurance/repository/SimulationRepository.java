package com.nsiago.assurance.repository;

import com.nsiago.assurance.domain.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation, Long> {
    Optional<Simulation> findByQuoteReference(String quoteReference);
} 