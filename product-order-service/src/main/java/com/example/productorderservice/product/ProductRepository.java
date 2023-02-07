package com.example.productorderservice.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
interface ProductRepository extends JpaRepository<Product, Long> {
//    private Map<Long, Product> persistence = new HashMap<>();
//    private Long sequence = 0L;
//
//    public void save(final Product product) {
//        product.assignId(++sequence);
//        persistence.put(product.getId(), product);
//    }
}
