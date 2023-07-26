package org.example.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataFirmBankingRequestRepository extends JpaRepository<FirmBankingRequestJpaEntity, Long> {
}
