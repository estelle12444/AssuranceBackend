package com.nsiago.assurance.repository;

import com.nsiago.assurance.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByAttestationNumber(String attestationNumber);
    Optional<Subscription> findBySimulationQuoteReference(String quoteReference);
} 