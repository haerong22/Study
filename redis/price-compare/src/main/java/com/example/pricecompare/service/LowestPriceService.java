package com.example.pricecompare.service;

import com.example.pricecompare.vo.Product;
import com.example.pricecompare.vo.ProductGroup;

import java.util.Set;

public interface LowestPriceService {

    Set getZsetValue(String key);

    int setNewProduct(Product product);

    int setNewProductGroup(ProductGroup productGroup);

    int setNewProductGroupToKeyword(String keyword, String prodGrpId, double score);
}
