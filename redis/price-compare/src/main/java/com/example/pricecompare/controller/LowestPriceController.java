package com.example.pricecompare.controller;

import com.example.pricecompare.service.LowestPriceService;
import com.example.pricecompare.vo.Keyword;
import com.example.pricecompare.vo.Product;
import com.example.pricecompare.vo.ProductGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class LowestPriceController {

    private final LowestPriceService lowestPriceService;

    @GetMapping("/zset")
    public Set getZsetValue(String key) {
        return lowestPriceService.getZsetValue(key);
    }

    @GetMapping("/product/lowest")
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        return lowestPriceService.getLowestPriceProductByKeyword(keyword);
    }

    @PutMapping("/product")
    public int setNewProduct(@RequestBody Product product) {
        return lowestPriceService.setNewProduct(product);
    }

    @PutMapping("/product/group")
    public int setNewProductGroup(@RequestBody ProductGroup productGroup) {
        return lowestPriceService.setNewProductGroup(productGroup);
    }

    @PutMapping("/product/group/keyword")
    public int SetNewProductGrpToKeyword (String keyword, String prodGrpId, double score) {
        return lowestPriceService.setNewProductGroupToKeyword(keyword, prodGrpId, score);
    }
}
