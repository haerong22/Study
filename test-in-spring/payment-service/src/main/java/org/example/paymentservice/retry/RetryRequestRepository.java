package org.example.paymentservice.retry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetryRequestRepository extends JpaRepository<RetryRequest, Long> {
}