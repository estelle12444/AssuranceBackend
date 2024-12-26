package com.nsiago.assurance.repository;

import com.nsiago.assurance.domain.Guarantee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GuaranteeRepository extends JpaRepository<Guarantee, Long> {
    Optional<Guarantee> findByName(String name);
} 