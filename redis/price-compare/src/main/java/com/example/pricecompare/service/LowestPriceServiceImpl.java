package com.example.pricecompare.service;

import com.example.pricecompare.vo.Product;
import com.example.pricecompare.vo.ProductGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LowestPriceServiceImpl implements LowestPriceService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Set getZsetValue(String key) {
        return redisTemplate.opsForZSet().rangeWithScores(key, 0, 9);
    }

    @Override
    public int setNewProduct(Product product) {
        redisTemplate.opsForZSet().add(product.getProductGroupId(), product.getProductId(), product.getPrice());
        return redisTemplate.opsForZSet().rank(product.getProductGroupId(), product.getProductId()).intValue();
    }

    @Override
    public int setNewProductGroup(ProductGroup productGroup) {
        productGroup.getProductList().forEach(p -> {
            redisTemplate.opsForZSet().add(productGroup.getProductGroupId(), p.getProductId(), p.getPrice());
        });
        return redisTemplate.opsForZSet().zCard(productGroup.getProductGroupId()).intValue();
    }

    @Override
    public int setNewProductGroupToKeyword(String keyword, String prodGrpId, double score) {
        redisTemplate.opsForZSet().add(keyword, prodGrpId, score);
        return redisTemplate.opsForZSet().rank(keyword, prodGrpId).intValue();
    }
}
