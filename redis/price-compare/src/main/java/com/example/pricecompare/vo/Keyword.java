package com.example.pricecompare.vo;

import lombok.Data;

import java.util.List;

@Data
public class Keyword {

    private String keyword;

    private List<ProductGroup> productGroupList;
}
