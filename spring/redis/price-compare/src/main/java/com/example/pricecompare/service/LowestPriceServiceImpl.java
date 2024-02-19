package com.example.pricecompare.service;

import com.example.pricecompare.vo.Keyword;
import com.example.pricecompare.vo.Product;
import com.example.pricecompare.vo.ProductGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LowestPriceServiceImpl implements LowestPriceService {

    private final RedisTemplate redisTemplate;

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

    @Override
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        Keyword returnInfo = new Keyword();
        List<ProductGroup> tempProdGrp = new ArrayList<>();
        // keyword 를 통해 ProductGroup 가져오기 (10개)
        tempProdGrp = getProdGrpUsingKeyword(keyword);

        // 가져온 정보들을 Return 할 Object 에 넣기
        returnInfo.setKeyword(keyword);
        returnInfo.setProductGroupList(tempProdGrp);
        // 해당 Object return
        return returnInfo;
    }

    private List<ProductGroup> getProdGrpUsingKeyword(String keyword) {

        List<ProductGroup> returnInfo = new ArrayList<>();

        // input 받은 keyword 로 productGrpId를 조회
        List<String> prodGrpIdList = List.copyOf(redisTemplate.opsForZSet().reverseRange(keyword, 0, 9));
        //Product tempProduct = new Product();
        List<Product> tempProdList = new ArrayList<>();

        //10개 prodGrpId로 loop
        for (final String prodGrpId : prodGrpIdList) {
            // Loop 타면서 ProductGrpID 로 Product:price 가져오기 (10개)

            ProductGroup tempProdGrp = new ProductGroup();

            Set prodAndPriceList = new HashSet();
            prodAndPriceList = redisTemplate.opsForZSet().rangeWithScores(prodGrpId, 0, 9);
            Iterator<Object> prodPricObj = prodAndPriceList.iterator();

            // loop 타면서 product obj에 bind (10개)
            while (prodPricObj.hasNext()) {
                ObjectMapper objMapper = new ObjectMapper();
                // {"value":00-10111-}, {"score":11000}
                Map<String, Object> prodPriceMap = objMapper.convertValue(prodPricObj.next(), Map.class);
                Product tempProduct = new Product();
                // Product Obj bind
                tempProduct.setProductId(prodPriceMap.get("value").toString()); // prod_id
                tempProduct.setPrice(Double.valueOf(prodPriceMap.get("score").toString()).intValue()); //es 검색된 score
                tempProduct.setProductGroupId(prodGrpId);

                tempProdList.add(tempProduct);
            }
            // 10개 product price 입력완료
            tempProdGrp.setProductGroupId(prodGrpId);
            tempProdGrp.setProductList(tempProdList);
            returnInfo.add(tempProdGrp);
        }

        return returnInfo;
    }
}
