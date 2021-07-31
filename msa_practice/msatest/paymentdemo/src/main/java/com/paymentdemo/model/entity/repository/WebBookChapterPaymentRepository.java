package com.paymentdemo.model.entity.repository;

import com.paymentdemo.model.entity.WebBookChapterPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebBookChapterPaymentRepository extends JpaRepository<WebBookChapterPayment,Long> {
}
