package com.example.loan.repository;

import com.example.loan.domain.Counsel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounselRepository extends JpaRepository<Counsel, Long> {
}
