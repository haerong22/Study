package com.example.rental.domain.event;

import com.example.rental.domain.model.vo.IDName;
import com.example.rental.domain.model.vo.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemRented implements Serializable {
    private IDName idName;
    private Item item;
    private long point;
}
