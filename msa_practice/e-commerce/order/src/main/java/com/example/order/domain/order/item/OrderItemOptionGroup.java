package com.example.order.domain.order.item;

import com.example.order.common.exception.InvalidParamException;
import com.example.order.domain.AbstractEntity;
import com.google.common.collect.Lists;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "order_item_option_groups")
public class OrderItemOptionGroup extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;
    private Integer ordering;
    private String itemOptionGroupName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItemOptionGroup", cascade = CascadeType.PERSIST)
    private List<OrderItemOption> orderItemOptionList = Lists.newArrayList();

    @Builder
    public OrderItemOptionGroup(
            OrderItem orderItem,
            Integer ordering,
            String itemOptionGroupName
    ) {
        if (orderItem == null) throw new InvalidParamException();
        if (ordering == null) throw new InvalidParamException();
        if (StringUtils.isEmpty(itemOptionGroupName)) throw new InvalidParamException();
                
        this.orderItem = orderItem;
        this.ordering = ordering;
        this.itemOptionGroupName = itemOptionGroupName;
    }

    public Long calculateTotalAmount() {
        return orderItemOptionList.stream()
                .mapToLong(OrderItemOption::getItemOptionPrice)
                .sum();
    }
}