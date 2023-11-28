package com.example.rental.domain.event;

import com.example.rental.domain.model.vo.IDName;
import com.example.rental.domain.model.vo.Item;
import lombok.Getter;

@Getter
public class ItemReturned extends ItemRented {

    public ItemReturned(IDName idName, Item item, long point) {
        super(idName, item, point);
    }
}
