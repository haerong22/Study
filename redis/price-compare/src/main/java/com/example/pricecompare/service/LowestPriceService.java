package com.example.pricecompare.service;

import com.example.pricecompare.vo.Product;

import java.util.Set;

public interface LowestPriceService {

    Set getZsetValue(String key);

    int setNewProduct(Product product);
}
