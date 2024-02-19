package com.example.loan.repository;

import com.example.loan.domain.Judgement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JudgementRepository extends JpaRepository<Judgement, Long> {

    Optional<Judgement> findByApplicationId(Long applicationId);
}
