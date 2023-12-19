package com.example.order.domain.item;

import com.example.order.common.exception.InvalidParamException;
import com.example.order.common.util.TokenGenerator;
import com.example.order.domain.AbstractEntity;
import com.google.common.collect.Lists;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "items")
public class Item extends AbstractEntity {
    private static final String ITEM_PREFIX = "itm_";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemToken;

    private Long partnerId;
    private String itemName;
    private Long itemPrice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.PERSIST)
    private List<ItemOptionGroup> itemOptionGroupList = Lists.newArrayList();

    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        PREPARE("판매 준비중"),
        ON_SALES("판매중"),
        END_OF_SALES("판매중"),
        ;

        private final String description;
    }

    @Builder
    public Item(Long partnerId, String itemName, Long itemPrice) {
        if (partnerId == null) throw new InvalidParamException("Item.partnerId");
        if (StringUtils.isBlank(itemName)) throw new InvalidParamException("Item.itemName");
        if (itemPrice == null) throw new InvalidParamException("Item.itemPrice");

        this.partnerId = partnerId;
        this.itemToken = TokenGenerator.randomCharacterWithPrefix(ITEM_PREFIX);
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.status = Status.PREPARE;
    }

    public void prepare() {
        this.status = Status.PREPARE;
    }

    public void onSales() {
        this.status = Status.ON_SALES;
    }

    public void endOfSales() {
        this.status = Status.END_OF_SALES;
    }
}
